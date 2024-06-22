<template>
    <v-md-preview :text="blog.content"></v-md-preview>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import request from '@/utils/request.js'
import {useRoute, useRouter} from 'vue-router';
import {ElMessage} from "element-plus";

const route = useRoute();
const router = useRouter();

let blog = reactive({})
let loading = ref(false)

//根据路径参数中的id 调用后端接口获取博客内容
const getBlog = () => {
    let id = route.params.id;
    blog.uid = id;
    if (id) {
        loading.value = true
        request.get('/web/preview/' + id).then(result => {
            if (result.code === '302') {
                ElMessage({
                    message: result.message,
                    type: 'warning',
                });
                router.push('/home')
                return;
            }
            Object.assign(blog, result.data);
            loading.value = false
        })
    }
}

onMounted(getBlog)
</script>

<style scoped>

</style>