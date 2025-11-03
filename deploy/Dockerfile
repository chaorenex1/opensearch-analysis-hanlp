FROM hub.icert.top/opensearchproject/opensearch:latest

# 使用 root 安装插件
USER root

# 复制由 mvn 打包生成的插件 zip 到镜像
COPY ./target/releases/analysis-hanlp.zip /tmp/analysis-hanlp.zip

# 安装插件
RUN unzip /tmp/analysis-hanlp.zip -d /usr/share/opensearch/plugins/opensearch-analysis-hanlp \
  && rm /tmp/analysis-hanlp.zip \
  && chown -R opensearch:opensearch /usr/share/opensearch/plugins/opensearch-analysis-hanlp


RUN mkdir -p /usr/share/opensearch/config/analysis-hanlp \
  && ls -l /usr/share/opensearch/plugins/opensearch-analysis-hanlp \
  && cp -r /usr/share/opensearch/plugins/opensearch-analysis-hanlp/config/* /usr/share/opensearch/config/analysis-hanlp/ \
  && chown -R opensearch:opensearch /usr/share/opensearch/config/analysis-hanlp

# 切回非特权用户
USER opensearch
