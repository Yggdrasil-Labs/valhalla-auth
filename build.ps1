# ============================================
# Valhalla Auth - 本地构建脚本 (Windows PowerShell)
# 仅负责构建，不负责推送
# 官方镜像由 CI 自动发布到 GHCR
# ============================================

param(
    [string]$Tag = "latest",
    [switch]$NoCache,
    [switch]$NoProxy
)

# 镜像命名规范：ghcr.io/<org>/<repo>
$IMAGE_NAME = "ghcr.io/yggdrasil-labs/valhalla-auth"
$FULL_TAG = "${IMAGE_NAME}:${Tag}"

$GITHUB_ACTOR = $env:GITHUB_ACTOR
$GITHUB_TOKEN = $env:GITHUB_TOKEN

# 检查认证信息（用于从 GitHub Packages 拉取依赖）
if (-not $GITHUB_ACTOR) {
    Write-Host "GITHUB_ACTOR not found in environment" -ForegroundColor Yellow
    $GITHUB_ACTOR = Read-Host "Enter GitHub username"
}

if (-not $GITHUB_TOKEN) {
    Write-Host "GITHUB_TOKEN not found in environment" -ForegroundColor Yellow
    $GITHUB_TOKEN = Read-Host "Enter GitHub Token" -AsSecureString
    $GITHUB_TOKEN = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($GITHUB_TOKEN))
}

# 获取版本信息
try {
    $REVISION = git rev-parse --short HEAD 2>$null
    if (-not $REVISION) { $REVISION = "unknown" }
} catch {
    $REVISION = "unknown"
}
$BUILD_DATE = (Get-Date).ToUniversalTime().ToString("yyyy-MM-ddTHH:mm:ssZ")

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "构建 Docker 镜像（仅本地构建，不推送）" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "镜像: $FULL_TAG" -ForegroundColor Green
Write-Host ""

# 启用 BuildKit（支持缓存挂载）
$env:DOCKER_BUILDKIT = "1"

$buildArgs = @(
    "build"
    "--build-arg", "GITHUB_ACTOR=$GITHUB_ACTOR"
    "--build-arg", "GITHUB_TOKEN=$GITHUB_TOKEN"
    "--build-arg", "VERSION=$Tag"
    "--build-arg", "REVISION=$REVISION"
    "--build-arg", "BUILD_DATE=$BUILD_DATE"
)

# 代理设置（Windows 本地构建可能需要）
if (-not $NoProxy) {
    $PROXY = "http://host.docker.internal:7890"
    Write-Host "Using proxy: $PROXY" -ForegroundColor Yellow
    $buildArgs += "--build-arg", "HTTP_PROXY=$PROXY"
    $buildArgs += "--build-arg", "HTTPS_PROXY=$PROXY"
}

$buildArgs += "-t", $FULL_TAG

if ($NoCache) {
    $buildArgs += "--no-cache"
}

$buildArgs += "."

docker @buildArgs

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "============================================" -ForegroundColor Green
    Write-Host "构建成功！" -ForegroundColor Green
    Write-Host "============================================" -ForegroundColor Green
    Write-Host "镜像: $FULL_TAG" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "运行命令:" -ForegroundColor White
    Write-Host "  docker run -d --name valhalla-auth ``" -ForegroundColor White
    Write-Host "    -p 8081:8081 -p 20880:20880 ``" -ForegroundColor White
    Write-Host "    -e SPRING_PROFILES_ACTIVE=dev ``" -ForegroundColor White
    Write-Host "    $FULL_TAG" -ForegroundColor White
    Write-Host ""
    Write-Host "注意: 本地构建仅用于开发测试" -ForegroundColor Yellow
    Write-Host "      官方镜像由 CI 自动发布: git tag v1.0.0; git push origin v1.0.0" -ForegroundColor Yellow
} else {
    Write-Host "`n构建失败！" -ForegroundColor Red
    exit 1
}
