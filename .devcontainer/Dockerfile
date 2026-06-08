FROM mcr.microsoft.com/devcontainers/java:1-8-bullseye

ARG SBT_VERSION=0.12.4
ARG ZULU7_URL=https://cdn.azul.com/zulu/bin/zulu7.56.0.11-ca-jdk7.0.352-linux_x64.tar.gz

ENV JAVA_HOME=/usr/local/zulu7
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN mkdir -p "${JAVA_HOME}" /usr/local/lib/sbt \
    && curl -fsSL "${ZULU7_URL}" \
        | tar -xz --strip-components=1 -C "${JAVA_HOME}" \
    && curl -fsSL "https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/${SBT_VERSION}/sbt-launch.jar" \
        -o /usr/local/lib/sbt/sbt-launch.jar \
    && printf '%s\n' \
        '[scala]' \
        '  version: ${sbt.scala.version-auto}' \
        '' \
        '[app]' \
        '  org: ${sbt.organization-org.scala-sbt}' \
        '  name: sbt' \
        '  version: ${sbt.version-read(sbt.version)[0.12.4]}' \
        '  class: ${sbt.main.class-sbt.xMain}' \
        '  components: xsbti,extra' \
        '  cross-versioned: ${sbt.cross.versioned-false}' \
        '' \
        '[repositories]' \
        '  local' \
        '  typesafe-ivy-releases: https://repo.typesafe.com/typesafe/ivy-releases/, [organization]/[module]/[revision]/[type]s/[artifact](-[classifier]).[ext]' \
        '  maven-central: https://repo1.maven.org/maven2/' \
        '  sonatype-snapshots: https://oss.sonatype.org/content/repositories/snapshots' \
        '' \
        '[boot]' \
        ' directory: ${sbt.boot.directory-${sbt.global.base-${user.home}/.sbt}/boot/}' \
        '' \
        '[ivy]' \
        '  ivy-home: ${sbt.ivy.home-${user.home}/.ivy2/}' \
        '  checksums: ${sbt.checksums-sha1,md5}' \
        '  override-build-repos: ${sbt.override.build.repos-false}' \
        '  repository-config: ${sbt.repository.config-${sbt.global.base-${user.home}/.sbt}/repositories}' \
        > /usr/local/lib/sbt/sbt.boot.properties \
    && printf '%s\n' \
        '[repositories]' \
        '  local' \
        '  maven-central: https://repo1.maven.org/maven2/' \
        '  sonatype-releases: https://oss.sonatype.org/content/repositories/releases' \
        '  sonatype-snapshots: https://oss.sonatype.org/content/repositories/snapshots' \
        '  scct-repository: https://mtkopone.github.io/scct/maven-repo/' \
        '  typesafe-ivy-releases: https://repo.typesafe.com/typesafe/ivy-releases/, [organization]/[module]/[revision]/[type]s/[artifact](-[classifier]).[ext]' \
        > /usr/local/lib/sbt/repositories \
    && mkdir -p /home/vscode/.sbt/plugins \
    && printf '%s\n' \
        'resolvers += "scct-github-repository" at "https://mtkopone.github.io/scct/maven-repo"' \
        '' \
        'addSbtPlugin("reaktor" % "sbt-scct" % "0.2-SNAPSHOT")' \
        > /home/vscode/.sbt/plugins/plugins.sbt \
    && chown -R vscode:vscode /home/vscode/.sbt \
    && printf '%s\n' '#!/usr/bin/env sh' 'exec /usr/local/zulu7/bin/java -Dhttps.protocols=TLSv1.2 -Dsbt.boot.properties=/usr/local/lib/sbt/sbt.boot.properties -Dsbt.override.build.repos=true -Dsbt.repository.config=/usr/local/lib/sbt/repositories -jar /usr/local/lib/sbt/sbt-launch.jar "$@"' \
        > /usr/local/bin/sbt \
    && chmod +x /usr/local/bin/sbt
