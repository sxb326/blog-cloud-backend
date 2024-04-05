<template>
    <button @click="handleClick">click</button>
    <h1>{{ res }}</h1>
    <base-no-data />
    <base-no-data>请先选择数据</base-no-data>
  </template>
  
  <script setup>
  import { getCurrentInstance } from 'vue';
  const { proxy } = getCurrentInstance();
  import { ref } from 'vue';

  let res = ref(null);
  
  async function handleClick() {
    res.value = await proxy.$api.test.time();
  }
  </script>