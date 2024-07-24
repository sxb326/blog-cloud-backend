<template>
    <el-dialog v-model="dialogVisible" title="登录/注册" width="30%" :destroy-on-close="true">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="auto" style="max-width: 600px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" type="password" />
            </el-form-item>
            <el-form-item style="text-align: center;">
                <el-button type="primary" @click="login">登录</el-button>
                <el-button @click="close">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script setup>
import { reactive, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

const dialogVisible = ref(false)
const open = () => {
    dialogVisible.value = true
}
defineExpose({
    open
})

let form = reactive({})

const emit = defineEmits(['refresh-page'])
const rules = reactive({
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

const formRef = ref(null);
//登录
const login = () => {
    formRef.value.validate((valid) => {
        if (!valid) {
            return false;
        }
        request.post('/auth/login', form).then(result => {
            const { code, message } = result;
            if (code === '0') {
                ElMessage({
                    message: message,
                    type: 'success',
                })
                emit('refresh-page')
                close();
                return;
            }
            ElMessage({
                message: message,
                type: 'error',
            })
        })
    });
}

const close = () => {
    form.username = '';
    form.password = '';
    dialogVisible.value = false
}
</script>

<style scoped></style>