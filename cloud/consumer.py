# Consumer
import boto3
import time

# Create SQS client
sqs = boto3.client('sqs', region_name='us-east-2')

queue_url = 'https://sqs.us-east-2.amazonaws.com/967716948421/homework6sqs'

# Even/Odd Counter
odd = 0
even = 0

# Run Forever
while True:
    # Receive message from SQS queue
    response = sqs.receive_message(
            QueueUrl=queue_url,
            AttributeNames=[
                'SentTimestamp'
            ],
            MaxNumberOfMessages=1,
            MessageAttributeNames=[
                'All'
            ],
            VisibilityTimeout=0,
            WaitTimeSeconds=0
        )

    # because message might be empty, we will TRY to fetch the next message
    try:
        # try to take the next message off the queue
        # note that the response is a dict, not a string
        message = response['Messages'][0]
        receipt_handle = message['ReceiptHandle']
        # Delete message from queue - we won't get this far if the queue was empty
        sqs.delete_message(
            QueueUrl=queue_url,
            ReceiptHandle=receipt_handle
        )
        # Convert the result to an integer
        result = int(message['Body'])

        # Add to Even/Odd counter
        if(result % 2 == 0):
            even = even + 1
        else:
            odd = odd + 1

        # Print out final results
        print("odd: "+str(odd)+", even: "+str(even))

    except KeyError:
        print('no messages on the queue')
        message = []
        # if no messages, take a nap for a minute before trying again
        time.sleep(60)
