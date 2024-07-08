<template>
    <el-dialog v-model="dialogVisible" title="收藏夹" width="30%" :destroy-on-close="true"
               :close-on-click-modal="false">
        收藏夹列表！！！
    </el-dialog>
</template>

<script setup>
import {ref} from "vue";

const dialogVisible = ref(false)
const open = () => {
    //调用接口 查询用户的收藏夹数据

    dialogVisible.value = true
}
defineExpose({
    open
})
</script>

<style scoped>

</style>