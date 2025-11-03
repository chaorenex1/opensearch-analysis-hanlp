#!/usr/bin/env bash
# 构建 OpenSearch HanLP 插件的 Docker 镜像
# 配置项（根据需要修改）
IMAGE_NAME="opensearch-hanlp"

# 颜色输出（可选）
GREEN="\033[0;32m"
YELLOW="\033[1;33m"
RED="\033[0;31m"
NC="\033[0m"

log() { echo -e "${GREEN}[INFO]${NC} $*"; }
warn() { echo -e "${YELLOW}[WARN]${NC} $*"; }
err() { echo -e "${RED}[ERROR]${NC} $*"; }

trap 'err "部署失败"; exit 1' ERR

log "开始构建项目包"
bash mvnw clean package -DskipTests

log "开始构建镜像 ${IMAGE_NAME}"

IMAGE_TAG="${IMAGE_NAME}:latest"
LAST_IMAGE_TAG="${IMAGE_TAG}"
if sudo docker images --format '{{.Repository}}:{{.Tag}}' | grep -q "^${LAST_IMAGE_TAG}$"; then
  log "上一次部署的镜像标签 ${LAST_IMAGE_TAG} 存在, 删除旧镜像"
  sudo docker rmi "${LAST_IMAGE_TAG}" || true
else
  LAST_IMAGE_TAG="none"
  log "未找到上一次部署的镜像标签"
fi

log "当前镜像标签 ${IMAGE_TAG}"

# 构建新镜像
log "构建镜像 ${IMAGE_TAG}"
sudo docker build -t "${IMAGE_TAG}" -f ./deploy/Dockerfile .

log "构建完成：镜像=${IMAGE_TAG}"