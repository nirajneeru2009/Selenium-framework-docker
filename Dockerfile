# Use a valid Maven image with OpenJDK 17
FROM maven:3.8.7-eclipse-temurin-17

# Set working directory inside the container
WORKDIR /usr/src/app

# Install dependencies and GNUPG
RUN apt-get update && apt-get install -y wget curl unzip gnupg ca-certificates

# Install Google Chrome (Fixing deprecated apt-key issue)
RUN mkdir -p /etc/apt/keyrings \
    && curl -fsSL https://dl.google.com/linux/linux_signing_key.pub | tee /etc/apt/keyrings/google-chrome.asc \
    && echo "deb [signed-by=/etc/apt/keyrings/google-chrome.asc] http://dl.google.com/linux/chrome/deb/ stable main" | tee /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install ChromeDriver
RUN CHROME_DRIVER_VERSION=$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE) && \
    wget -q https://chromedriver.storage.googleapis.com/${CHROME_DRIVER_VERSION}/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    chmod +x chromedriver && \
    mv chromedriver /usr/local/bin/ && \
    rm chromedriver_linux64.zip

# Copy project files (pom.xml and source code)
COPY . .

# Run tests using Maven
CMD ["mvn", "clean", "test"]