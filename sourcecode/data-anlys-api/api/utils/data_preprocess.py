#!/usr/bin/env python3

import pandas as pd
from pandas.io.json import json_normalize

from api.models import variant as models
from api.utils import app_util


def merge_data(genes):
    res = genes.pop(0)
    for g in genes:
        res = pd.merge(res, g, how='inner', on=['gene_name', 'gene_id'])
        res.dropna(inplace=True)
    return res.sort_values(by=['gene_name'])


def normalize(df):
    df.columns = range(df.shape[1])
    return (df - df.mean()) / df.std()


def prepare_data(items):
    genes = []
    files = []

    for item in items:
        file_name = item['fileName']
        files.append(file_name)
        df = pd.DataFrame.from_dict(json_normalize(item['omics']), orient='columns')
        df = df.rename(columns={'geneId': 'gene_id', 'geneName': 'gene_name', 'ref': 'reference', 'tpm': file_name})
        # print (df)
        # df.to_csv(file_name, sep='\t', encoding='utf-8')
        genes.append(df)

    return files, genes


def clustergrammer(files, items):
    genes = []

    for item in items:
        item = item.drop(['reference', 'start', 'end'], axis=1)
        genes.append(item)

    merge_df = merge_data(genes)

    temp = merge_df[merge_df.columns.intersection(files)]
    merge_df = merge_df.loc[(temp != 0).any(axis=1), :].reset_index(drop=True)
    df = merge_df.set_index(['gene_name'], drop=False)

    gene_df = df[df.columns.intersection(files)]
    sample_df = gene_df.transpose()

    norm_gene = normalize(gene_df)
    norm_sample = normalize(sample_df)

    return norm_gene, norm_sample, df


def ideogram(files, items, gene_id):
    genes = []

    for i, item in enumerate(items):
        file_name = files[i]

        if file_name in item.columns:
            item = item.drop(file_name, axis=1)
            item = pd.merge(gene_id, item, how='inner', on=['gene_id'])
            genes.append(item)

    merge_df = pd.concat(genes, ignore_index=True)
    merge_df = merge_df.drop_duplicates()

    return merge_df


def tmb_score(items, exomesize):
    variants = []
    types = ['missense', 'stop_gained', 'frameshift_variant']

    num_all_variants = count_unique_variants(items)
    rate_all_variants = app_util.normal_round(num_all_variants / exomesize, 2)
    variants.append(models.Variant(None, 'all_variants', 'No. of all variants', num_all_variants, rate_all_variants))
    # print("No. of all variants : ", num_all_variants, num_all_variants/exomesize)

    # indels = list(filter(lambda x: len(x['refAllele']) > 1 or len(x['altAllele']) > 1, items))
    # num_indels = count_unique_variants(indels)
    # rate_indels = app_util.normal_round(num_indels * 1.0 / num_all_variants, 2)
    #
    # rate_snvs = app_util.normal_round((num_all_variants - num_indels) * 1.0 / num_all_variants, 2)

    protein_coding_variants = get_variants_by_fields(items, 'biotype', 'protein_coding')
    num_protein_coding = count_unique_variants(protein_coding_variants)
    rate_protein_coding = app_util.normal_round(num_protein_coding / exomesize, 2)
    variants.append(models.Variant(None, 'protein_coding', 'No. of variants in protein coding', num_protein_coding, rate_protein_coding))
    # print("No. of variants in protein coding: ", num_protein_coding, num_protein_coding/exomesize)

    for type in types:
        specific_variants = get_variants_by_fields(items, 'consequence', type)
        num_specific_variants = count_unique_variants(specific_variants)
        rate_specific_variants = app_util.normal_round(num_specific_variants / exomesize, 2)
        level_specific_variants = get_tmb_level(rate_specific_variants)
        variants.append(models.Variant(level_specific_variants, 'variants_w_' + type, "No. of variants w/ " + type, num_specific_variants, rate_specific_variants))
        # print("No. of variants w/ " + type + " : ", num_specific_variants, num_specific_variants/exomesize)

        num_threshold_1 = count_tumor_variants_by_threshold(specific_variants, 0.01)
        rate_threshold_1 = app_util.normal_round(num_threshold_1 / exomesize, 2)
        level_threshold_1 = get_tmb_level(rate_threshold_1)
        variants.append(
            models.Variant(level_threshold_1, 'variants_w_' + type + '_1', "No. of variants w/ " + type + " w/ vaf>=1%",
                           num_threshold_1, rate_threshold_1))
        # print("No. of variants w/ " + type + " w/ vaf>=1% : ", num_threshold_1, num_threshold_1/exomesize)

        num_threshold_5 = count_tumor_variants_by_threshold(specific_variants, 0.05)
        rate_threshold_5 = app_util.normal_round(num_threshold_5 / exomesize, 2)
        level_threshold_5 = get_tmb_level(rate_threshold_5)
        variants.append(models.Variant(level_threshold_5, 'variants_w_' + type + '_5', "No. of variants w/ " + type + " w/ vaf>=5%", num_threshold_5, rate_threshold_5))
        # print("No. of variants w/ " + type + " w/ vaf>=5% : ", num_threshold_5, num_threshold_5/exomesize)

    # print("% of SNVs = ", str((num_all_variants - num_indels) * 1.0 / num_all_variants))
    # print("% of indels = ", str(num_indels * 1.0 / num_all_variants))

    # variants.append(models.Variant('indels', '% of indels', num_indels, rate_indels))
    # variants.append(models.Variant('snvs', '% of SNVs', None, rate_snvs))

    return variants


def count_unique_variants(items):
    no_dupes = set([item['chromosome'] + '_' + str(item['position']) for item in items])
    return len(no_dupes)


def get_variants_by_fields(items, key, value):
    filtered_list = list(filter(lambda x: str(x[key]).find(value) > -1, items))
    return filtered_list


def count_tumor_variants_by_threshold(items, threshold):
    filtered_list = list(filter(lambda x: x['tumor']['alleleFrequency'] >= threshold, items))
    num_unique_variants = count_unique_variants(filtered_list)
    return num_unique_variants


def get_tmb_level(rate):
    if rate >= 1 and rate <= 5:
        return 'low'
    elif rate >= 6 and rate <= 10:
        return 'intermediate'
    elif rate >= 20:
        return 'high'
    else:
        return None
