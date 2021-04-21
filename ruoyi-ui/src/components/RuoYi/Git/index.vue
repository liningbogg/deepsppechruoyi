<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'RuoYiGit',
  data() {
    return {
      url: 'https://github.com/liningbogg/deepsppechruoyi'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>