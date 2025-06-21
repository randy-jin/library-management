# deploy.sh
#!/bin/bash

echo "Applying Kubernetes configurations..."

# Apply ConfigMap
kubectl apply -f kubernetes/configmap.yaml

# Apply Deployment
kubectl apply -f kubernetes/deployment.yaml

# Apply Service
kubectl apply -f kubernetes/service.yaml

# Apply Ingress
kubectl apply -f kubernetes/ingress.yaml

echo "Deployment completed!"

# Check deployment status
kubectl rollout status deployment/library-management

# Get pods
kubectl get pods -l app=library-management

# Get services
kubectl get svc library-management-service

# Get ingress
kubectl get ingress library-management-ingress