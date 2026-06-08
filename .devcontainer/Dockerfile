# Scala 2.9.2 (see build.sbt) only runs correctly on JDK 8, so we pin the
# Microsoft Java 8 dev container image, which ships JDK 8 + SDKMAN.
FROM mcr.microsoft.com/devcontainers/java:8

# Install sbt (the project's build tool) from the official sbt apt repository.
RUN curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" \
        | gpg --dearmor -o /usr/share/keyrings/sbt-keyring.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/sbt-keyring.gpg] https://repo.scala-sbt.org/scalasbt/debian all main" \
        > /etc/apt/sources.list.d/sbt.list \
    && apt-get update \
    && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends sbt \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
