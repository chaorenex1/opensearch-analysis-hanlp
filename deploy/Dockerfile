FROM hub.icert.top/opensearchproject/opensearch:latest

# 使用 root 安装插件
USER root

# 复制由 mvn 打包生成的插件 zip 到镜像
COPY ./target/releases/analysis-hanlp.zip /tmp/analysis-hanlp.zip

# 安装插件、清理临时文件并修正权限
RUN /usr/share/opensearch/bin/opensearch-plugin install file:///tmp/analysis-hanlp.zip --batch \
  && rm /tmp/analysis-hanlp.zip \
  && chown -R opensearch:opensearch /usr/share/opensearch/plugins/opensearch-analysis-hanlp \
  && chown -R opensearch:opensearch /usr/share/opensearch/config/opensearch-analysis-hanlp
# 切回非特权用户
USER opensearch
