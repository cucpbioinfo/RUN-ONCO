#!/usr/bin/env python3

from flask import Blueprint, request, jsonify
from api.utils import app_util, data_preprocess as preprocessor

clustergrammer_api = Blueprint('clustergrammer_api', __name__)

@clustergrammer_api.route("/clustergrammer", methods=['POST'])
def clustergrammer():
    req = request.get_json()

    files, items = preprocessor.prepare_data(req['sampleOmics'])
    norm_gene, norm_sample, df = preprocessor.clustergrammer(files, items)

    sample_group = app_util.execute_module(norm_sample, req['sampleCluster'])
    gene_group = app_util.execute_module(norm_gene, req['geneCluster'])

    clustergrammer = app_util.clustergrammer(files, sample_group, gene_group, df)

    gene_id = df[['gene_id']]
    df_ideogram = preprocessor.ideogram(files, items, gene_id)
    container, datatable = app_util.ideogram(df_ideogram, gene_group)

    output = {
        "clustergrammer": clustergrammer,
        "ideogram": {
            "container": container,
            "datatable": datatable
        }
    }

    return jsonify(output), 200