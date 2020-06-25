#!/usr/bin/env python3

from sklearn.cluster import KMeans

def process(feature, config):
    
    print('----- KMEANS -----')
    
    '''Destructive parameter'''
    n_clusters = int(config['n_clusters'])
    init = config['init']
    random_state = None if config['random_state'] == "None" else int(config['random_state'])
    algorithm = config['algorithm']
    
    print(n_clusters, init, random_state, algorithm)
    
    kmeans = KMeans(n_clusters=n_clusters, init=init, random_state=random_state, algorithm=algorithm ).fit(feature)
    return kmeans.predict(feature)
