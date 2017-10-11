<template>

  <div>
    <h2>Sentiment Analysis</h2>
    
    <vue-toastr ref='toastr'></vue-toastr>
    
    <div class="home">
      <span class="chart">
        <bar-chart :chart-data="datacollection"></bar-chart>
      </span>

      <div class="Tweettable">
        <vue-good-table
          title="Tweet Table"
          :columns="columns"
          :rows="tweets"
          :paginate="true"
          :lineNumbers="true"/>
      </div>

    </div>

 </div>

</template>

<script>
let TWEETS_URI = 'http://localhost:8187/tweets'

import axios from 'axios'
import BarChart from './BarChart.js'
import _ from 'lodash'
import Vue from 'vue'
import VueToastr from 'vue-toastr'
import VueGoodTable from 'vue-good-table'

Vue.use(VueGoodTable)

export default ({
  name: 'home',
  components: {
    VueToastr,
    BarChart
  },
  data () {
    return {
      tweets: 'Tweet list',
      datacollection: null,
      columns: [
        {
          label: 'Message',
          field: 'message'
        },
        {
          label: 'Sentiment',
          field: 'score'
        }
      ]
    }
  },
  created: function () {
    this.getTweets()
  },
  mounted: function () {
    this.$refs.toastr.Add({
      title: 'Pending...',
      msg: 'searching for tweets',
      clickClose: false, // Click Close Disable
      timeout: 5000, // Remember defaultTimeout is 5 sec..
      position: 'toast-bottom-center',
      type: 'success'
    })
  },
  methods: {
    getTweets: function () {
      axios.get(TWEETS_URI, {
        'withCredentials': false
      })
        .then((resp) => {
          this.tweets = resp.data
          var sentiments = this.tweets.map(function (tweet) { return tweet.score }).sort()
          var nbValueBySentiment = _.countBy(sentiments, _.identity)

          this.datacollection = {
            labels: Object.keys(nbValueBySentiment),
            datasets: [
              {
                label: 'Sentiments',
                backgroundColor: '#00a1b3',
                data: Object.values(nbValueBySentiment)
              }
            ]
          }
        })
        .catch((err) => {
          console.error(err.message)
        })
    }
  }
})

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
html {
  width: 100%;
  height: 100%;
}
h2 {
  font-weight: bold;
}

</style>
