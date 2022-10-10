#!/bin/bash
gcloud compute ssh ubuntu@rbb-testnet-sevm-4 --command='pkill rbbc || echo "Error in killing process"' < /dev/null    
gcloud compute ssh ubuntu@rbb-testnet-rbbc-4 --command='pkill rbcore || echo "Error in killing process"' < /dev/null