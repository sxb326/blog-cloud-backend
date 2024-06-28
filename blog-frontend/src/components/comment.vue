<template>
    <el-drawer v-model="drawerVisible" :title="'评论（' + count + '）'" direction="rtl">
        <div v-infinite-scroll="load" infinite-scroll-distance="10" style="overflow: auto; height: calc(100vh - 100px)">
            <div v-for="item in data" :key="item.uid">
              {{ item }}
            </div>
        </div>
    </el-drawer>
</template>

<script setup>
import {ref} from "vue";
import request from '@/utils/request.js'

const drawerVisible = ref(false)

let blogId = ref('')
let count = ref(0)
let page = ref(1)

const load = () => {
    page.value++
    getData()
}

const open = (id) => {
    blogId.value = id
    getData()
    drawerVisible.value = true
}

let data = ref([])
const getData = () => {
    request.get('/web/comment/' + blogId.value + '/' + page.value).then(result => {
        count.value = result.data.count
        data.value.push(...result.data.data)
    })
}
defineExpose({
    open
})
</script>

<style scoped>

</style>