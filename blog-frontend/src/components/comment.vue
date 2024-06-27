<template>
    <el-drawer v-model="drawerVisible" :title="'评论（' + comment.count + '）'" direction="rtl">
        <el-table :data="comment.data" height="calc(100vh - 150px)" style="width: 100%" :show-header="false"
                  v-infinite-scroll="load">
            <el-table-column>
                <template #default="scope">
                    {{ scope.row }}
                </template>
            </el-table-column>
        </el-table>
    </el-drawer>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from '@/utils/request.js'

// const imgUrl = import.meta.env.VITE_APP_SERVICE_API + '/picture/';
const drawerVisible = ref(false)

let comment = reactive({})
let page = ref(1)

const load = () => {
console.log("懒加载")
}

const open = (id) => {
    getData(id);
    drawerVisible.value = true
}

const getData = (id) => {
    request.get('/web/comment/' + id + '/' + page.value).then(result => {
        comment.count = result.data.count
        comment.data = result.data.data
        // if (result.code === '302') {
        //     ElMessage({
        //         message: result.message,
        //         type: 'warning',
        //     });
        //     router.push('/home')
        //     return;
        // }
        // Object.assign(blog, result.data);
        // nextTick(() => {
        //     directoryInit()
        //     loading.value = false
        // })
    })
}
defineExpose({
    open
})
</script>

<style scoped>

</style>