/**
 * 存储学期对象到本地。
*/
async function setData() {
    const mode = document.querySelector("#university-selector").value
    const term = await Term.getInstance(mode, document.querySelector("#excel-selector").files[0])
    localStorage.setItem("cy-data", JSON.stringify(term))
}

/**
 * 获取存储在本地的学期对象。
 * @returns {Term | null}
*/
function getData() {
    const d = localStorage.getItem("cy-data")
    if (d)
        return new Term(d)
    return null
}

/**
 * 渲染指定时间内，学期中可用的切片。
 * @param {Date} time 指定的时间
*/
function render(time) {
    const term = getData()
    const cutsToday = term.getTodayCuts(time)
    const timeByd = new Date(time.getTime() + 24 * 60 * 60 * 1000)
    const cutsTomorrow = term.getTodayCuts(timeByd)
    let today = document.querySelector("#today")
    let tomorrow = document.querySelector("#tomorrow")
    const getChinese = (num) => {
        return {
            1: "一",
            2: "二",
            3: "三",
            4: "四",
            5: "五",
            6: "六",
            0: "日"
        }[num]
    }
    const judgeWeekend = (day) => {
        if (day == 6 || day == 0)
            return "weekend"
        return ""
    }
    const judgeWeek = (time) => {
        const week = term.getWeek(time)
        if (week > 0)
            return `第${week}周 `
        return `开学前${-week}周 `
    }
    today.innerHTML = `
        <div class="date ${judgeWeekend(time.getDay())}">
            ${time.getMonth() + 1}月${time.getDate()}日， ${judgeWeek(time)}周${getChinese(time.getDay())}
        </div>
        <div class="unit-title">今天</div>
    `
    tomorrow.innerHTML = `
        <div class="date ${judgeWeekend(timeByd.getDay())}">
            ${timeByd.getMonth() + 1}月${timeByd.getDate()}日，${judgeWeek(timeByd)}周${getChinese(timeByd.getDay())}
        </div>
        <div class="unit-title">明天</div>
    `
    if (cutsToday.length)
        cutsToday.forEach(c => {
            today.innerHTML += `
                <div class="cut">${c.time[0]}-${c.time[1]}\t${c.name}：${c.place}（${c.teacher}）</div>
            `
        })
    else
        today.innerHTML += `<div class="cut">空闲！</div>`
    if (cutsTomorrow.length)
        cutsTomorrow.forEach(c => {
            tomorrow.innerHTML += `
                <div class="cut">${c.time[0]}-${c.time[1]}\t${c.name}：${c.place}（${c.teacher}）</div>
            `
        })
    else
        tomorrow.innerHTML += `<div class="cut">空闲！</div>`
    setInterval(() => {
        render(new Date())
    }, 1800000)
}

/**
 * 重置请求缓存
 * @type {Date[]}
*/
let resetRequest = []

/**
 * 重置程序。只能由用户在控制台*或者自定义控件监听*里被调用。
*/
function reset() {
    if (confirm("您正在做的操作将导致程序重置，确定要这么做吗？")) {
        document.querySelector("#f-reset").click()
        resetRequest = []
    }
}

window.addEventListener("keydown", function (event) {
    const secLim = 1
    const timesLim = 8
    if (event.key === "r" || event.key === "R") {
        resetRequest.push(Date.now())
        if (resetRequest.length >= timesLim) {
            let lastThreeResetRequest = resetRequest.slice(-timesLim);
            if (lastThreeResetRequest[timesLim - 1] - lastThreeResetRequest[0] <= secLim * 1000)
                reset()
        }
        if (resetRequest.length >= timesLim + 1)
            resetRequest.shift()
    }
})