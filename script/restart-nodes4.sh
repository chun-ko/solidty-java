#!/bin/bash
gcloud compute ssh ubuntu@rbb-testnet-sevm-4 --command='pkill rbbc || echo "Error in killing process"' < /dev/null    
gcloud compute ssh ubuntu@rbb-testnet-rbbc-4 --command='pkill rbcore || echo "Error in killing process"' < /dev/null    

gcloud compute ssh ubuntu@rbb-testnet-rbbc-4 --command='chmod +x ./binaries/rbcore' < /dev/null       
gcloud compute ssh ubuntu@rbb-testnet-rbbc-4 --command=' nohup bash ./run.sh > ./go/src/rbbcLogs 2>&1 &' < /dev/null  

gcloud compute ssh ubuntu@rbb-testnet-sevm-4 --command='chmod +x ./binaries/rbbc' < /dev/null  
gcloud compute ssh ubuntu@rbb-testnet-sevm-4 --command='nohup bash ./observe-recovery.sh > ./go/src/rbbcLogs 2>&1 &' < /dev/null  
        
