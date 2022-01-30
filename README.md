# test-aws-s3

Simple AWS S3 java sdk usage

## Development Setup

Mock S3 service with Docker:

```
docker run -p 9090:9090 -p 9191:9191 -v LOCAL_DIR:/s3mockroot -e initialBuckets=Bucket-1 -t adobe/s3mock
```