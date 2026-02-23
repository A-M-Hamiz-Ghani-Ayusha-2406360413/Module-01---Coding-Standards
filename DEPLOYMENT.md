# Deployment Guide

This guide explains how to deploy the E-Shop application to various PaaS platforms.

## Prerequisites

- A PaaS account (Render, Koyeb, or similar)
- Git repository connected to your PaaS platform
- Docker Hub account (for push-based deployment)

## Deployment Options

### Option 1: Render (Pull-Based - Recommended)

Render automatically pulls code from your GitHub repository and deploys it.

#### Setup Steps:

1. **Connect Repository to Render**
   - Go to [Render Dashboard](https://dashboard.render.com/)
   - Click "New +" → "Blueprint"
   - Connect your GitHub repository
   - Render will detect the `render.yaml` file automatically

2. **Automatic Deployment**
   - Every push to the `main` branch triggers automatic deployment
   - Render builds the Docker image and deploys it
   - View logs and status in the Render dashboard

3. **Access Your Application**
   - After deployment, Render provides a URL (e.g., `https://eshop-app.onrender.com`)
   - The application is accessible at this URL

#### Configuration

The `render.yaml` file contains:
- Service type: Web service
- Runtime: Docker
- Auto-deploy: Enabled
- Health check endpoint: `/`
- Environment variables for production

### Option 2: Koyeb (Pull-Based)

Koyeb also supports pull-based deployment from GitHub.

#### Setup Steps:

1. **Connect Repository to Koyeb**
   - Go to [Koyeb Dashboard](https://app.koyeb.com/)
   - Click "Create App"
   - Select "GitHub" as source
   - Choose your repository and branch (`main`)

2. **Configure Deployment**
   - Koyeb will detect the `koyeb.yaml` file
   - Alternatively, configure manually:
     - Build type: Dockerfile
     - Dockerfile path: `./Dockerfile`
     - Port: 8080

3. **Deploy**
   - Click "Deploy"
   - Koyeb builds and deploys the application
   - Auto-deploy is enabled for future pushes

### Option 3: Docker Hub + Manual PaaS (Push-Based)

For platforms that don't support direct GitHub integration, use the push-based approach.

#### Setup Steps:

1. **Configure GitHub Secrets**
   
   Add the following secrets to your GitHub repository:
   - `DOCKER_USERNAME`: Your Docker Hub username
   - `DOCKER_PASSWORD`: Your Docker Hub password or access token
   
   To add secrets:
   - Go to your GitHub repository
   - Navigate to Settings → Secrets and variables → Actions
   - Click "New repository secret"

2. **Workflow Execution**
   
   The `.github/workflows/deploy.yml` workflow:
   - Triggers on push to `main` branch
   - Builds the Docker image
   - Pushes to Docker Hub (`<username>/eshop:latest`)

3. **Deploy to PaaS**
   
   Configure your PaaS to pull the image from Docker Hub:
   - Image: `<your-docker-username>/eshop:latest`
   - Port: 8080

## Docker Configuration

### Dockerfile

The `Dockerfile` uses a multi-stage build with security best practices:

1. **Builder Stage**: Uses JDK 21 Alpine to build the application with Gradle
2. **Runner Stage**: Uses JRE 21 Alpine for a smaller image size
3. **Security**: Creates a non-root user (`advshop`) to run the application
4. **Permissions**: Sets proper file ownership and permissions

### Building Locally

To test the Docker build locally:

```bash
# Build the image
docker build -t eshop:local .

# Run the container
docker run -p 8080:8080 eshop:local

# Access the application
# Open http://localhost:8080 in your browser
```

## Verification

After deployment, verify:

1. **PMD Scanning**: Check that the PMD workflow runs successfully
   - No violations should be reported
   - Workflow status: ✅ Passed

2. **Application Health**: Access the deployed URL
   - Home page should load
   - Product functionality should work

3. **Deployment Logs**: Check PaaS platform logs for errors

## Environment Variables

The following environment variables are configured:

- `SPRING_PROFILES_ACTIVE=production`: Activates production profile
- `SERVER_PORT=8080`: Application listens on port 8080

Add additional environment variables as needed in:
- `render.yaml` for Render
- `koyeb.yaml` for Koyeb
- PaaS dashboard for other platforms

## Workflow Files

### `.github/workflows/pmd-scanning.yml`
- Runs PMD static code analysis
- Triggers on every push to any branch
- Uploads PMD reports as artifacts

### `.github/workflows/ci.yml`
- Runs unit tests
- Generates code coverage reports (JaCoCo)
- Triggers on push and pull requests

### `.github/workflows/deploy.yml`
- Builds Docker image
- Pushes to Docker Hub
- Triggers on push to `main` branch
- Can be manually triggered via workflow_dispatch

## Troubleshooting

### Build Fails

- Check Dockerfile syntax
- Verify Gradle build passes locally: `./gradlew bootJar`
- Review build logs in PaaS dashboard

### Deployment Fails

- Verify port 8080 is exposed correctly
- Check environment variables are set
- Review application logs for errors

### Application Not Accessible

- Verify health check endpoint is correct
- Check if the service is running in PaaS dashboard
- Review network/firewall settings

## Free Tier Limitations

Both Render and Koyeb offer free tiers with limitations:

- **Render Free Tier**:
  - 750 hours/month
  - Services spin down after inactivity
  - May experience cold starts

- **Koyeb Free Tier**:
  - 1 web service
  - Limited resources
  - Auto-sleep after inactivity

For production workloads, consider upgrading to paid plans.

## Additional Resources

- [Render Documentation](https://docs.render.com/)
- [Koyeb Documentation](https://www.koyeb.com/docs)
- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
