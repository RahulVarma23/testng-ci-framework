FROM openjdk:15-jdk-slim

# Install required packages
RUN apt-get update && \
    apt-get install -y wget gnupg2 curl unzip && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    apt-get install -y xvfb && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Install Maven
ENV MAVEN_VERSION 3.8.8
RUN wget https://dlcdn.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    mv apache-maven-$MAVEN_VERSION /usr/local/maven

# Add Maven to PATH
ENV PATH="/usr/local/maven/bin:${PATH}"

# Create directories for test results
RUN mkdir -p /app/target/test-results \
    /app/target/extent-reports \
    /app/target/screenshots

# Set display for running browser in container
ENV DISPLAY=:99

# Start Xvfb and run tests
CMD Xvfb :99 -screen 0 1920x1080x24 > /dev/null 2>&1 & \
    mvn clean test