<template>
  <el-row class="topBar">
    <el-col :span="2">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </el-col>
    <el-col :span="12" class="menu">
      <div class="menuItem"><router-link to="/">首页</router-link></div>
      <div class="menuItem"><router-link to="/test">专栏</router-link></div>
    </el-col>
    <el-col :span="6">
      <el-input v-model="keyWord" style="width: 240px" placeholder="想搜索点什么呢">
        <template #suffix>
          <el-icon class="el-input__icon" style="cursor: pointer;" @click="doSearch">
            <search />
          </el-icon>
        </template>
      </el-input>
    </el-col>
    <el-col :span="1">
      <el-icon size="25px"><Bell /></el-icon>
    </el-col>
    <el-col :span="1">
      <el-icon size="25px"><User /></el-icon>
    </el-col>
  </el-row>
  <router-view/>
</template>

<script setup>
import { User,Search } from '@element-plus/icons-vue';
import { ref } from 'vue';

let keyWord = ref("");

// 搜索
function doSearch() {
  alert('触发搜索,关键字:' + keyWord.value)
}

</script>

<style scoped>
.topBar {
  border-bottom: 1px solid #afafaf;
  margin-bottom: 10px;
}

.menu {
  display: flex
}

.menuItem {
  margin: 0 10px;
  color: red;
}

.router-link-active {
  text-decoration: none;
  color: #646cff;
}

a {
  text-decoration: none;
  color: black;
}
</style>
