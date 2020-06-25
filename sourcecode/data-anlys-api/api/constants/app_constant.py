#!/usr/bin/env python3

# PARAM_SAMPLE = {
#     "algorithm": "api/algorithms/kmean.py",
#     "config": {
#         "n_clusters": "2",
#         "init": "k-means++",
#         "random_state": "10",
#         "algorithm": "auto"
#     }
# }
#
# PARAM_GENE = {
#     "algorithm": "api/algorithms/kmean.py",
#     "config": {
#         "n_clusters": "2",
#         "init": "k-means++",
#         "random_state": "10",
#         "algorithm": "auto"
#     }
# }

STRINGDB_REQUEST_TEMPLATE = "http://string-db.org/api/{0}/{1}?identifiers={2}&species={3}&caller_identity={4}"
