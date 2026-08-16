<template>
    <div class="content">
        <div class="title">哎呀，页面走丢了~</div>
    </div>
</template>

<script lang='ts' setup>
</script>

<style lang="less" scoped>
.content {
    background-image: url('@/assets/404.jpg');
    // 背景图片覆盖整个容器（容器是div）
    background-size: cover;
    // 设置div占满整个父容器body
    height: 100vh;
    // 背景图片居中显示
    background-position: center;
    .title {
        text-align: center;
        font-size: 28px;
        color: #888;
        padding-top: 150px;
    }
}
</style>