#!/usr/bin/env python3

from sklearn.cluster import AgglomerativeClustering


def process(feature, config):
    print('----- AGGLOMERATIVE -----')

    '''Destructive parameter'''
    n_clusters = int(config['n_clusters'])
    affinity = config['affinity']
    linkage = config['linkage']

    print(n_clusters, affinity, linkage)

    hc = AgglomerativeClustering(n_clusters=n_clusters, affinity=affinity, linkage=linkage).fit(feature)

    return hc.fit_predict(feature)
