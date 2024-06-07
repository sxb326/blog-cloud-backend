<template>
    <el-container style="height: 100vh;">
        <el-aside width="200px" class="aside-container" style="margin-right: 20px;">
            <h3>分类列表</h3>
        </el-aside>
        <el-container style="display: flex; flex-direction: row;">
            <el-main style="flex: 1; padding: 0 20px;background-color: #FFF;" class="main-container">
                <h3>文章列表</h3>
            </el-main>
            <el-aside width="200px" class="aside-container" style="margin-left: 20px;">
                <h3>热门文章</h3>
            </el-aside>
        </el-container>
    </el-container>
</template>
<style>
body {
    background-color: #f2f3f5;
}

.el-container {
    display: flex;
    align-items: stretch;
}

.main-container {
    border-radius: 5px;
}

.aside-container {
    height: calc(100vh - 110px);
    overflow-y: auto;
    background-color: #FFF;
    padding-bottom: 50px;
    position: relative;
    border-radius: 5px;
}
</style>