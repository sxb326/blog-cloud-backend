module.exports = {
    root: true,
    env: {
      browser: true,
      es2021: true,
      node: true,
    },
    extends: ['eslint:recommended', 'plugin:vue/vue3-recommended', 'prettier'],
    overrides: [],
    parserOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
    },
    plugins: ['vue', 'prettier'],
    rules: {
      // 'prettier/prettier': 'error', //这句配置是继承了prettier的规则 一般不用prettier就不需要配置
      'vue/multi-word-component-names': 'off',
      'vue/no-v-model-argument': 'off',
    },
  };