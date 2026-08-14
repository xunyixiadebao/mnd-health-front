<template>
    <!-- 二级路由出口 -->
    <RouterView :key="router.currentRoute.value.fullPath"/>
</template>

<script lang='ts' setup>
import router from '@/router';

</script>

<style lang="less" scoped>
// 一定要注意末尾的分号，不能丢。
@import url('main.less');
</style>