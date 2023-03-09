# Publisher 
import boto3
import random
import time

# Create an SNS client
sns = boto3.client('sns')

# Iterate 1000 times
for i in range(0,1000):
    
    # generate a random number between 1-100
    x = random.randint(1,100)
    
    # Output random number
    print(str(i+1)+") "+str(x))

    # Publish a simple message to the specified SNS topic
    response = sns.publish(
        TopicArn='arn:aws:sns:us-east-2:967716948421:homework6',    
        Message=str(x)    
    )

    # Sleep for 5 seconds
    time.sleep(5)

