import sys
from typing import Final

import requests

OWNER: Final = "refeyn"
REPO: Final = "lw8"
QUERY: Final = """
query SubmoduleCommits($owner: String!, $repo: String!, $ref: String!) {
  repository(owner: $owner, name: $repo) {
    root: object(expression: $ref) {
      ... on Commit {
        oid
        submodules(first: 100) {
          nodes {
            name
            subprojectCommitOid
          }
        }
      }
    }
  }
}
"""

ENDPOINT: Final = "https://api.github.com/graphql"

_, ghtoken, ref = sys.argv

r = requests.post(
    ENDPOINT,
    json={
        "query": QUERY,
        "variables": {"owner": OWNER, "repo": REPO, "ref": ref},
    },
    headers={"Authorization": f"token {ghtoken.strip()}"},
    timeout=60,
)
root = r.json()["data"]["repository"]["root"]
print(f"REFEYN_{REPO.upper()}_COMMIT={root['oid']}")
for submodule in root["submodules"]["nodes"]:
    name = submodule["name"].split("/")[-1]
    commit = submodule["subprojectCommitOid"]
    print(f"REFEYN_{name.upper()}_COMMIT={commit}")
