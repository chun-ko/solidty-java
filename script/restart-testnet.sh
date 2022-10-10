#!/bin/bash

echo "Retrieve IP"

touch sevmInstanceName.txt
touch rbbcInstanceName.txt
rm -rf sevmInstanceName.txt
rm -rf rbbcInstanceName.txt
echo "$(gcloud compute instances list --filter=Name:'rbb-testnet-sevm*' --format='get(name)')" >> sevmInstanceName.txt
echo "$(gcloud compute instances list --filter=Name:'rbb-testnet-rbbc*' --format='get(name)')" >> rbbcInstanceName.txt

echo "Stop SEVM nodes"
input="./sevmInstanceName.txt"
result='^[0-9]+$'
while IFS= read -r line
do 
    gcloud compute ssh ubuntu@$line --command='pkill rbbc || echo "Error in killing process"' < /dev/null    
done < "$input"

echo "Stop Consensus Nodes"
input="./rbbcInstanceName.txt"
result='^[0-9]+$'
while IFS= read -r line
do 
    gcloud compute ssh ubuntu@$line --command='pkill rbcore || echo "Error in killing process"' < /dev/null    
done < "$input"

echo "Start Consensus Nodes"
input="./rbbcInstanceName.txt"
while IFS= read -r line
do  
   gcloud compute ssh ubuntu@$line --command='chmod +x ./binaries/rbcore' < /dev/null       
   gcloud compute ssh ubuntu@$line --command=' nohup bash ./run.sh > ./go/src/rbbcLogs 2>&1 &' < /dev/null  
done < "$input"  


echo "Start SEVM nodes"
input="./sevmInstanceName.txt"
while IFS= read -r line
do  
   gcloud compute ssh ubuntu@$line --command='chmod +x ./binaries/rbbc' < /dev/null  
   gcloud compute ssh ubuntu@$line --command='nohup bash ./observe.sh > ./go/src/rbbcLogs 2>&1 &' < /dev/null  
done < "$input"
        
