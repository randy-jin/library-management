# Build and deployment scripts
# build.sh
#!/bin/bash

echo "Building Docker image..."
docker build -t library-management:latest .

echo "Tagging image..."
docker tag library-management:latest library-management:v1.0.0

echo "Build completed successfully!"