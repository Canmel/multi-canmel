$(function () {
    zfhqs();
    qytdfbt();
    zfzl();
    wrapMessages();
    sbtdqk();
    tdgddbt();
});

function wrapMessages() {
    $("#warning-messages").Scroll({
        line: 1,
        speed: 500,
        timer: 2000
    });
}

function tdgddbt() {
    var myChart = echarts.init(document.getElementById('tdgddbt'));
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['停电次数','工单数量'],
            textStyle: {
                color: '#cacaca'
            }
        },
        calculable: true,
        backgroundColor: '#4e64696b',
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: ['7:00', '8:00', '9:00', '10:00', '11:00', '12:00', '13:00'],
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: '#cacaca'
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: '#cacaca'
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '停电次数',
                type: 'line',
                data: [7003, 7993, 8002, 8332, 8443, 9333, 10002],
                itemStyle: {
                    normal: {
                        color: 'red',
                        lineStyle: {
                            color: "red"
                        }
                    }
                }
            },
            {
                name: '工单数量',
                type: 'line',
                data: [2134, 3434, 5453, 6753, 8765, 9001, 9921],
                itemStyle: {
                    normal: {
                        color: '#cacaca',
                        lineStyle: {
                            color: "#cacaca"
                        }
                    }
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function sbtdqk() {
    var tqtd = echarts.init(document.getElementById("tqtdqk"));
    var lytd = echarts.init(document.getElementById("lytdqk"));
    var yhtd = echarts.init(document.getElementById("yhtdqk"));
    var option = {
        calculable: true,
        animation: false,
        series: [
            {
                type: 'pie',
                hoverAnimation: false,
                center: ['50%', '50%'],
                radius: ['60%', '86%'],
                x: '0%', // for funnel
                itemStyle: {
                    normal: {
                        label: {
                            formatter: function (params) {
                                return params.value + '%'
                            },
                            textStyle: {
                                baseline: 'top'
                            }
                        }
                    }
                },
                data: [
                    {
                        name: '',
                        value: 23,
                        itemStyle: {
                            normal: {
                                color: '#fff000',
                                label: {
                                    show: true,
                                    position: 'center',
                                    formatter: '{b}',
                                    textStyle: {
                                        color: 'red',
                                        baseline: 'bottom'
                                    }
                                },
                                labelLine: {
                                    show: false
                                },
                                label: {
                                    show: true,
                                    position: 'center'
                                }
                            }
                        }
                    },
                    {
                        name: '停电',
                        value: 77,
                        itemStyle: {
                            normal: {
                                color: 'rgba(0,0,0,0)',
                                label: {
                                    show: true,
                                    position: 'center',
                                    formatter: '{b}',
                                    textStyle: {
                                        color: 'red',
                                        baseline: 'bottom'
                                    }
                                },
                                labelLine: {
                                    show: false
                                },
                                label: {
                                    show: true,
                                    position: 'center'
                                }
                            }
                        }
                    }

                ]
            }
        ]
    };

    tqtd.setOption(option, false);
    lytd.setOption(option, false);
    yhtd.setOption(option, false);
}

function zfzl() {
    var zfzlChart = echarts.init(document.getElementById('zfzl'));
    var option = {
        tooltip: {
            formatter: "{a} <br/>{c} {b}"
        },
        backgroundColor: '#4e64696b',
        series: [
            {
                type: 'gauge',
                z: 3,
                center: ['50%', '75%'],    // 默认全局居中
                radius: '120%',
                min: 0,
                max: 100,
                startAngle: 180,
                endAngle: 0,
                splitNumber: 10,
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: [[0.2, '#228b22'], [0.8, '#48b'], [1, '#ff4500']],
                        width: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title: {
                    show: false,
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        align: 'center',
                        baseline: 'middle',
                        fontSize: 20,
                        color: '#fff',
                        fontStyle: 'italic'
                    }
                },
                detail: {
                    offsetCenter: ['0%', '-40%'],
                    formatter: function (a) {
                        return a + '%';
                    },
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        color: '#fff000'
                    },
                    offsetCenter: ['-0%', '-10%']
                },
                data: [{value: 72, name: '总负载率'}]
            }
        ]
    };
    zfzlChart.setOption(option);
}

function qytdfbt() {
    var barChart = echarts.init(document.getElementById('qytdfbt'));
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        backgroundColor: '#4e64696b',
        textStyle: {
            color: '#ffffff'
        },
        grid: {x: '15%', y: '15%', width: '80%', height: '70%'},
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: ['海曙', '鄞州', '北仑', '镇海', '奉化', '江北', '慈溪'],
                axisLabel: {
                    textStyle: {
                        color: '#ffffff'
                    }
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    inside: false,
                    length: 5,
                    lineStyle: {
                        color: '#00f',
                        shadowColor: '#00f',
                        shadowOffsetY: -5
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    textStyle: {
                        color: '#ffffff'
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '用户总数',
                type: 'bar',
                data: [7003, 7993, 8002, 8332, 8443, 9333, 10002],
                itemStyle: {
                    normal: {
                        color: "#5589C6",
                        barBorderRadius: [10, 10, 10, 10]
                    },
                    emphasis: {
                        barBorderRadius: [10, 10, 10, 10]
                    }
                },
                barMaxWidth: '30',
                barGap: '1%'
            },
            {
                name: '停电户数',
                type: 'bar',
                data: [212, 322, 422, 712, 341, 223, 293],
                itemStyle: {
                    normal: {
                        color: "#c23531",
                        barBorderRadius: [10, 10, 10, 10]
                    },
                    emphasis: {
                        barBorderRadius: [10, 10, 10, 10]
                    }
                },
                barMaxWidth: '30',
                barGap: '1%'
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    barChart.setOption(option);
}

function zfhqs() {
    var myChart = echarts.init(document.getElementById('fhqst'));
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        calculable: true,
        backgroundColor: '#4e64696b',
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: ['7:00', '8:00', '9:00', '10:00', '11:00', '12:00', '13:00'],
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: '#ffffff'
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: '#ffffff'
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '负载',
                type: 'line',
                data: [7003, 7993, 8002, 8332, 8443, 9333, 10002],
                markPoint: {
                    data: [
                        {name: '最低', value: -2, xAxis: 1, yAxis: -1.5}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}