#!/usr/bin/env python3

import importlib.util
import sys
import math


def load_module(path):
    loader = None

    try:
        spec = importlib.util.spec_from_file_location("module_name", path)
        loader = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(loader)
    except ImportError:
        print("Failed to load {module}".format(module="module_name"), file=sys.stderr)

    return loader


def execute_module(df, param):
    algorithm = param['algorithm']
    config = param['config']
    path = 'api/algorithms/{}.py'.format(algorithm)
    model = load_module(path)
    return model.process(df, config)


def clustergrammer(files, sample_group, gene_group, df):
    rows = []
    cols = []

    mat = [df[x].tolist() for x in files]

    for i, filename in enumerate(files):
        row = {
            'group': [str(sample_group[i])] * 11,
            'name': filename,
            'clust': int(sample_group[i]),
            'rank': int(i),
            'init': int(i)
        }
        rows.append(row)

    # for i, gene in enumerate(df.index.values):
    for i, gene in enumerate(df['gene_name']):
        col = {
            'group': [str(gene_group[i])] * 11,
            'name': gene,
            'clust': int(gene_group[i]),
            'rank': int(i),
            'init': int(i)
        }
        cols.append(col)

    container = {
        'mat': mat,
        'links': [],
        'row_nodes': rows,
        'col_nodes': cols,
        'enrichrgram': False
    }

    return container


def ideogram(gene_pos, grp_gene):
    dt = []
    container = []
    color = ["red", "blue", "black", "pink", "yellow", "green", "purple"]

    # print ('------------')
    # print (len(grp_gene))
    # print ('------------')
    # print (len(gene_pos))

    for i, group in enumerate(grp_gene):
        # print (gene_pos['gene_name'][i])
        row = {
            "name": gene_pos['gene_name'][i],
            "chr": gene_pos['reference'][i],
            "start": int(gene_pos['start'][i]),
            "stop": int(gene_pos['end'][i]),
            "color": color[group]
        }
        container.append(row)

        row = {
            "key": gene_pos['gene_name'][i],
            "id": gene_pos['gene_id'][i],
            "name": gene_pos['gene_name'][i],
            "ref": gene_pos['reference'][i],
            "group": int(group + 1)
        }
        dt.append(row)

    return container, dt


def cystoscape(network_data, types):
    container = []
    gene = set()

    options = {
        "combine": 5,
        "neighborhood": 6,
        "fusion": 7,
        "phylogenetic": 8,
        "coexpression": 9,
        "experimental": 10,
        "database": 11,
        "textmining": 12
    }
    colors = {
        "combine": "violet",
        "neighborhood": "blue",
        "fusion": "yellow",
        "phylogenetic": "black",
        "coexpression": "red",
        "experimental": "orange",
        "database": "gray",
        "textmining": "pink"
    }

    for field in types:
        score_type = options[field]
        for edge in network_data:
            if (float(edge[score_type]) > 0.1):
                p1, p2 = edge[2], edge[3]
                if (p1 not in gene):
                    temp = {
                        "data": {"id": p1, "label": p1},
                        "position": {"x": 10, "y": 10}
                    }
                    container.append(temp)
                    gene.add(p1)
                if (p2 not in gene):
                    temp = {
                        "data": {"id": p2, "label": p2},
                        "position": {"x": 10, "y": 10}
                    }
                    container.append(temp)
                    gene.add(p2)
                temp = {
                    "data": {
                        "source": p1,
                        "target": p2,
                        "label": 'Edge from Node1 to Node2',
                        "color": colors[field],
                        "score": float(edge[score_type]) * 10
                    }
                }
                container.append(temp)

    return container

def normal_round(n, decimals=0):
    expoN = n * 10 ** decimals
    if abs(expoN) - abs(math.floor(expoN)) < 0.5:
        return math.floor(expoN) / 10 ** decimals
    return math.ceil(expoN) / 10 ** decimals