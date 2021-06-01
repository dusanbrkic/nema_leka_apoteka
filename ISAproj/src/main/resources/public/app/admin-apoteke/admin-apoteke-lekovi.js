Vue.component("AdminApotekeLekovi", {
  extends: VueChartJs.Bar,
  props: ["data", "labels", "options", "label"],
  mounted() {
    this.renderLineChart();
  },
  computed: {
    chartData: function () {
      return this.data;
    },
    chartLabels: function () {
      return this.labels;
    },
    chartLabel: function () {
      return this.label;
    },
  },
  methods: {
    renderLineChart: function () {
      this.renderChart(
        {
          labels: this.chartLabels,
          datasets: [
            {
              label: this.chartLabel,
              backgroundColor: "#f87979",
              data: this.chartData,
            },
          ],
        },
        { responsive: true, maintainAspectRatio: false }
      );
    },
  },
  watch: {
    data: function () {
      //this._chart.destroy();
      console.log(this.data);
      console.log(this.options);
      //this.renderChart(this.data, this.options);
      this.renderLineChart();
    },
  },
});
