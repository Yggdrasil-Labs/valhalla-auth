#!/bin/bash
# ============================================
# Valhalla Auth - 本地构建脚本 (Linux/Mac)
# 仅负责构建，不负责推送
# 官方镜像由 CI 自动发布到 GHCR
# ============================================

set -e

# 镜像命名规范：ghcr.io/<org>/<repo>
IMAGE_NAME="ghcr.io/yggdrasil-labs/valhalla-auth"
TAG="${1:-latest}"
FULL_TAG="${IMAGE_NAME}:${TAG}"

# 检查认证信息（用于从 GitHub Packages 拉取依赖）
if [ -z "$GITHUB_ACTOR" ] || [ -z "$GITHUB_TOKEN" ]; then
    echo "请设置环境变量: GITHUB_ACTOR 和 GITHUB_TOKEN"
    echo "示例: export GITHUB_ACTOR=YoungerYang-Y"
    echo "      export GITHUB_TOKEN=ghp_xxx"
    exit 1
fi

echo "============================================"
echo "构建 Docker 镜像（仅本地构建，不推送）"
echo "============================================"
echo "镜像: $FULL_TAG"
echo ""

# 启用 BuildKit
export DOCKER_BUILDKIT=1

docker build \
    --build-arg GITHUB_ACTOR="$GITHUB_ACTOR" \
    --build-arg GITHUB_TOKEN="$GITHUB_TOKEN" \
    --build-arg VERSION="${TAG}" \
    --build-arg REVISION="$(git rev-parse --short HEAD 2>/dev/null || echo 'unknown')" \
    --build-arg BUILD_DATE="$(date -u +%Y-%m-%dT%H:%M:%SZ)" \
    -t "$FULL_TAG" \
    .

echo ""
echo "============================================"
echo "构建成功！"
echo "============================================"
echo "镜像: $FULL_TAG"
echo ""
echo "运行命令:"
echo "  docker run -d --name valhalla-auth \\"
echo "    -p 8081:8081 -p 20880:20880 \\"
echo "    -e SPRING_PROFILES_ACTIVE=dev \\"
echo "    $FULL_TAG"
echo ""
echo "注意: 本地构建仅用于开发测试"
echo "      官方镜像由 CI 自动发布: git tag v1.0.0 && git push origin v1.0.0"

