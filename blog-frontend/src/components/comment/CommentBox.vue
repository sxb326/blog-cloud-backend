<template>
    <el-form class="commentDiv" ref="formRef" :model="comment" :rules="rules">
        <el-form-item prop="content">
            <el-input v-model="comment.content" :rows="3" type="textarea" placeholder="评论一下吧" maxlength="1000"
                      show-word-limit/>
        </el-form-item>
        <el-button size="small" type="primary" style="margin-top: 5px;float: right" @click="save">发表</el-button>
    </el-form>
</template>

<script setup>
import {onMounted, reactive, ref, defineProps} from "vue";

let comment = reactive({blogUid: '', parentUid: '', replyToUid: '', content: ''})

const props = defineProps({
    blogUid: {type: String, required: true},
    parentUid: {type: String, required: true},
    replyToUid: {type: String, required: true}
})

onMounted(() => {
    comment.blogUid = props.blogUid
    comment.parentUid = props.parentUid
    comment.replyToUid = props.replyToUid
})

const rules = reactive({
    content: [{required: true, message: '请输入评论内容', trigger: 'blur'}]
})

const formRef = ref(null);

const save = () => {
    formRef.value.validate((valid) => {
        if (!valid) {
            return false;
        }

    });
}

</script>

<style scoped>
.commentDiv {
    margin: 5px;
}
</style>