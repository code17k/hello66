// ========== 数据定义 ==========
const factorRanking = [
  { name: '钩子时长', weight: 0.156, category: 'content' },
  { name: '冲突密度', weight: 0.142, category: 'content' },
  { name: '叙事节奏', weight: 0.128, category: 'content' },
  { name: '题材类型', weight: 0.115, category: 'content' },
  { name: '发布时段', weight: 0.098, category: 'platform' },
  { name: '封面设计', weight: 0.087, category: 'content' },
  { name: '推荐机制适配', weight: 0.076, category: 'platform' },
  { name: '话题运营', weight: 0.065, category: 'platform' },
  { name: '用户画像匹配', weight: 0.058, category: 'user' },
  { name: '热点关联', weight: 0.042, category: 'external' },
  { name: '单集时长', weight: 0.033, category: 'content' }
];

const genreData = {
  urban: { name: '都市情感', views: 156, completion: 68, engagement: 12.5 },
  fantasy: { name: '奇幻玄幻', views: 89, completion: 52, engagement: 8.2 },
  rural: { name: '乡村题材', views: 134, completion: 72, engagement: 15.3 },
  suspense: { name: '悬疑推理', views: 112, completion: 61, engagement: 10.8 },
  comedy: { name: '喜剧搞笑', views: 145, completion: 75, engagement: 18.6 },
  history: { name: '古装历史', views: 98, completion: 58, engagement: 9.4 }
};

const platformData = [
  { name: '抖音', samples: 4520, percentage: 45.2, color: '#fe2c55' },
  { name: '快手', samples: 2890, percentage: 28.9, color: '#ff4906' },
  { name: '微信视频号', samples: 1680, percentage: 16.8, color: '#07c160' },
  { name: 'B站', samples: 910, percentage: 9.1, color: '#00a1d6' }
];

const timeData = [
  { period: '早高峰(7-9点)', views: 85, completion: 58, engagement: 8.5 },
  { period: '午休时段(11-13点)', views: 102, completion: 64, engagement: 10.2 },
  { period: '晚高峰(18-21点)', views: 135, completion: 72, engagement: 14.8 },
  { period: '深夜时段(22-24点)', views: 98, completion: 68, engagement: 12.3 }
];

// ========== 工具函数 ==========
function scrollToSection(sectionId) {
  document.getElementById(sectionId).scrollIntoView({ behavior: 'smooth' });
}

function animateCounter(element, target, duration = 2000) {
  let start = 0;
  const increment = target / (duration / 16);
  
  function updateCounter() {
    start += increment;
    if (start >= target) {
      element.textContent = target;
      return;
    }
    element.textContent = Math.floor(start);
    requestAnimationFrame(updateCounter);
  }
  
  updateCounter();
}

function animateProgress() {
  const progressBars = document.querySelectorAll('.progress-fill');
  progressBars.forEach(bar => {
    const target = bar.getAttribute('data-target');
    setTimeout(() => {
      bar.style.width = target + '%';
    }, 300);
  });
}

// ========== 入场动画 ==========
function handleReveal() {
  const reveals = document.querySelectorAll('.reveal');
  
  reveals.forEach(reveal => {
    const windowHeight = window.innerHeight;
    const elementTop = reveal.getBoundingClientRect().top;
    const elementVisible = 150;
    
    if (elementTop < windowHeight - elementVisible) {
      reveal.classList.add('visible');
    }
  });
}

// ========== 背景Canvas ==========
function initBackgroundCanvas() {
  const canvas = document.getElementById('bg-canvas');
  const ctx = canvas.getContext('2d');
  
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  
  const particles = [];
  const particleCount = 100;
  
  for (let i = 0; i < particleCount; i++) {
    particles.push({
      x: Math.random() * canvas.width,
      y: Math.random() * canvas.height,
      size: Math.random() * 2 + 1,
      speedX: Math.random() * 0.5 - 0.25,
      speedY: Math.random() * 0.5 - 0.25,
      color: `rgba(16, 185, 129, ${Math.random() * 0.3 + 0.1})`
    });
  }
  
  function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    particles.forEach(particle => {
      ctx.fillStyle = particle.color;
      ctx.beginPath();
      ctx.arc(particle.x, particle.y, particle.size, 0, Math.PI * 2);
      ctx.fill();
      
      particle.x += particle.speedX;
      particle.y += particle.speedY;
      
      if (particle.x > canvas.width) particle.x = 0;
      if (particle.x < 0) particle.x = canvas.width;
      if (particle.y > canvas.height) particle.y = 0;
      if (particle.y < 0) particle.y = canvas.height;
    });
    
    requestAnimationFrame(animate);
  }
  
  animate();
}

// ========== 因素排名 ==========
function renderFactorRanking() {
  const container = document.getElementById('factor-ranking');
  if (!container) return;
  
  factorRanking.forEach((factor, index) => {
    const barContainer = document.createElement('div');
    barContainer.className = 'flex items-center gap-4';
    
    const rank = document.createElement('div');
    rank.className = 'w-8 text-center font-bold text-[var(--muted)]';
    rank.textContent = index + 1;
    
    const info = document.createElement('div');
    info.className = 'flex-1';
    
    const name = document.createElement('div');
    name.className = 'text-sm font-medium';
    name.textContent = factor.name;
    
    const bar = document.createElement('div');
    bar.className = 'mt-2 progress-bar';
    
    const fill = document.createElement('div');
    fill.className = 'progress-fill';
    fill.style.background = getCategoryColor(factor.category);
    fill.style.width = '0%';
    fill.setAttribute('data-target', (factor.weight * 100).toFixed(1));
    
    bar.appendChild(fill);
    info.appendChild(name);
    info.appendChild(bar);
    
    const weight = document.createElement('div');
    weight.className = 'w-16 text-right font-mono text-sm text-[var(--muted)]';
    weight.textContent = (factor.weight * 100).toFixed(1) + '%';
    
    barContainer.appendChild(rank);
    barContainer.appendChild(info);
    barContainer.appendChild(weight);
    container.appendChild(barContainer);
  });
  
  // 动画显示
  setTimeout(() => {
    const fills = container.querySelectorAll('.progress-fill');
    fills.forEach(fill => {
      const target = fill.getAttribute('data-target');
      fill.style.width = target + '%';
    });
  }, 500);
}

function getCategoryColor(category) {
  const colors = {
    content: 'linear-gradient(to right, var(--accent), #059669)',
    platform: 'linear-gradient(to right, var(--accent-secondary), #0e7490)',
    user: 'linear-gradient(to right, var(--warning), #d97706)',
    external: 'linear-gradient(to right, #ec4899, #db2777)'
  };
  return colors[category] || colors.content;
}

// ========== 预测模型 ==========
function initPredictionForm() {
  const predictBtn = document.getElementById('predict-btn');
  if (!predictBtn) return;
  
  predictBtn.addEventListener('click', async () => {
    // 获取表单数据
    const genre = document.getElementById('genre-select').value;
    const duration = parseInt(document.getElementById('duration-slider').value);
    const hook = parseInt(document.getElementById('hook-slider').value);
    const pacing = parseInt(document.getElementById('pacing-slider').value);
    const conflict = parseInt(document.getElementById('conflict-slider').value);
    const time = document.getElementById('time-select').value;
    const model = document.querySelector('.model-option.selected').dataset.model;
    
    // 构建请求数据
    const requestData = {
      genre,
      duration,
      hook,
      pacing,
      conflict,
      time,
      model
    };
    
    const resultElements = {
      views: document.getElementById('result-views'),
      completion: document.getElementById('result-completion'),
      engagement: document.getElementById('result-engagement'),
      fans: document.getElementById('result-fans')
    };
    
    // 显示加载状态
    Object.values(resultElements).forEach(el => {
      el.textContent = '计算中...';
    });
    predictBtn.disabled = true;
    
    try {
      // 发送请求到后端
      const response = await fetch('/api/predict', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      });
      
      if (!response.ok) {
        throw new Error('服务器响应失败');
      }
      
      const data = await response.json();
      
      // 更新UI
      resultElements.views.textContent = data.views.toFixed(1);
      resultElements.completion.textContent = data.completion.toFixed(1) + '%';
      resultElements.engagement.textContent = data.engagement.toFixed(1) + '%';
      resultElements.fans.textContent = data.fans.toFixed(0);
      
      // 更新图表
      updatePredictionChart(data);
    } catch (error) {
      console.error('预测失败:', error);
      Object.values(resultElements).forEach(el => {
        el.textContent = '错误';
      });
      alert('预测失败，请稍后重试');
    } finally {
      predictBtn.disabled = false;
    }
  });
  
  // 模型选择
  const modelOptions = document.querySelectorAll('.model-option');
  modelOptions.forEach(option => {
    option.addEventListener('click', () => {
      modelOptions.forEach(opt => opt.classList.remove('selected'));
      option.classList.add('selected');
    });
  });
  
  // 滑块事件
  const sliders = {
    duration: document.getElementById('duration-slider'),
    hook: document.getElementById('hook-slider'),
    pacing: document.getElementById('pacing-slider'),
    conflict: document.getElementById('conflict-slider')
  };
  
  const values = {
    duration: document.getElementById('duration-value'),
    hook: document.getElementById('hook-value'),
    pacing: document.getElementById('pacing-value'),
    conflict: document.getElementById('conflict-value')
  };
  
  const pacingLabels = ['极慢', '缓慢', '中速', '快速', '极快'];
  
  if (sliders.duration) {
    sliders.duration.addEventListener('input', () => {
      values.duration.textContent = sliders.duration.value;
    });
  }
  
  if (sliders.hook) {
    sliders.hook.addEventListener('input', () => {
      values.hook.textContent = sliders.hook.value;
    });
  }
  
  if (sliders.pacing) {
    sliders.pacing.addEventListener('input', () => {
      values.pacing.textContent = pacingLabels[sliders.pacing.value - 1];
    });
  }
  
  if (sliders.conflict) {
    sliders.conflict.addEventListener('input', () => {
      values.conflict.textContent = sliders.conflict.value;
    });
  }
}

// ========== 图表初始化 ==========
function initCharts() {
  // 预测结果图表
  updatePredictionChart();
  
  // 热力图
  initHeatmapChart();
  
  // 平台分布
  initPlatformChart();
  
  // 题材对比
  initGenreChart();
  
  // 时段效果
  initTimeChart();
}

function updatePredictionChart(predictionData) {
  const ctx = document.getElementById('prediction-chart');
  if (!ctx) return;
  
  // 使用后端返回的数据或默认值
  const predicted = predictionData ? 
    [predictionData.views, predictionData.completion, predictionData.engagement, predictionData.fans] : 
    [125, 65, 12, 850];
  
  const average = [95, 58, 9, 620];
  
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['播放量(万)', '完播率(%)', '互动率(%)', '粉丝转化'],
      datasets: [
        {
          label: '预测值',
          data: predicted,
          backgroundColor: 'rgba(16, 185, 129, 0.7)',
          borderColor: 'rgba(16, 185, 129, 1)',
          borderWidth: 1
        },
        {
          label: '行业均值',
          data: average,
          backgroundColor: 'rgba(122, 139, 163, 0.5)',
          borderColor: 'rgba(122, 139, 163, 1)',
          borderWidth: 1
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        },
        x: {
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        }
      },
      plugins: {
        legend: {
          labels: {
            color: 'rgba(240, 244, 248, 0.8)'
          }
        }
      }
    }
  });
}

function initHeatmapChart() {
  const ctx = document.getElementById('heatmap-chart');
  if (!ctx) return;
  
  // 模拟相关性数据
  const data = [
    [1.00, 0.75, 0.62, 0.58, 0.45],
    [0.75, 1.00, 0.68, 0.52, 0.41],
    [0.62, 0.68, 1.00, 0.71, 0.38],
    [0.58, 0.52, 0.71, 1.00, 0.32],
    [0.45, 0.41, 0.38, 0.32, 1.00]
  ];
  
  const labels = ['播放量', '完播率', '互动率', '粉丝转化', '话题热度'];
  
  new Chart(ctx, {
    type: 'matrix',
    data: {
      datasets: [{
        label: '相关性热力图',
        data: data.flatMap((row, i) => 
          row.map((value, j) => ({
            x: labels[j],
            y: labels[i],
            v: value
          }))
        ),
        backgroundColor(context) {
          const value = context.dataset.data[context.dataIndex].v;
          const alpha = value;
          return `rgba(16, 185, 129, ${alpha})`;
        },
        borderColor: 'rgba(42, 53, 72, 0.5)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          type: 'category',
          labels: labels,
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        },
        y: {
          type: 'category',
          labels: labels,
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        }
      },
      plugins: {
        legend: {
          display: false
        }
      }
    }
  });
}

function initPlatformChart() {
  const ctx = document.getElementById('platform-chart');
  if (!ctx) return;
  
  new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: platformData.map(item => item.name),
      datasets: [{
        data: platformData.map(item => item.percentage),
        backgroundColor: platformData.map(item => item.color),
        borderColor: 'rgba(10, 15, 26, 1)',
        borderWidth: 2
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'right',
          labels: {
            color: 'rgba(240, 244, 248, 0.8)',
            padding: 20
          }
        }
      }
    }
  });
}

function initGenreChart() {
  const ctx = document.getElementById('genre-chart');
  if (!ctx) return;
  
  const genres = Object.keys(genreData);
  
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: genres.map(key => genreData[key].name),
      datasets: [
        {
          label: '播放量(万)',
          data: genres.map(key => genreData[key].views),
          backgroundColor: 'rgba(16, 185, 129, 0.7)',
          borderColor: 'rgba(16, 185, 129, 1)',
          borderWidth: 1
        },
        {
          label: '完播率(%)',
          data: genres.map(key => genreData[key].completion),
          backgroundColor: 'rgba(6, 182, 212, 0.7)',
          borderColor: 'rgba(6, 182, 212, 1)',
          borderWidth: 1
        },
        {
          label: '互动率(%)',
          data: genres.map(key => genreData[key].engagement),
          backgroundColor: 'rgba(245, 158, 11, 0.7)',
          borderColor: 'rgba(245, 158, 11, 1)',
          borderWidth: 1
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        },
        x: {
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        }
      },
      plugins: {
        legend: {
          labels: {
            color: 'rgba(240, 244, 248, 0.8)'
          }
        }
      }
    }
  });
}

function initTimeChart() {
  const ctx = document.getElementById('time-chart');
  if (!ctx) return;
  
  new Chart(ctx, {
    type: 'line',
    data: {
      labels: timeData.map(item => item.period),
      datasets: [
        {
          label: '播放量指数',
          data: timeData.map(item => item.views),
          borderColor: 'rgba(16, 185, 129, 1)',
          backgroundColor: 'rgba(16, 185, 129, 0.1)',
          tension: 0.4,
          fill: true
        },
        {
          label: '完播率(%)',
          data: timeData.map(item => item.completion),
          borderColor: 'rgba(6, 182, 212, 1)',
          backgroundColor: 'rgba(6, 182, 212, 0.1)',
          tension: 0.4,
          fill: true
        },
        {
          label: '互动率(%)',
          data: timeData.map(item => item.engagement),
          borderColor: 'rgba(245, 158, 11, 1)',
          backgroundColor: 'rgba(245, 158, 11, 0.1)',
          tension: 0.4,
          fill: true
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        },
        x: {
          grid: {
            color: 'rgba(42, 53, 72, 0.5)'
          },
          ticks: {
            color: 'rgba(122, 139, 163, 0.8)'
          }
        }
      },
      plugins: {
        legend: {
          labels: {
            color: 'rgba(240, 244, 248, 0.8)'
          }
        }
      }
    }
  });
}

// ========== 页面初始化 ==========
document.addEventListener('DOMContentLoaded', function() {
  // 初始化背景Canvas
  initBackgroundCanvas();
  
  // 初始化因素排名
  renderFactorRanking();
  
  // 初始化预测表单
  initPredictionForm();
  
  // 初始化图表
  initCharts();
  
  // 入场动画
  handleReveal();
  window.addEventListener('scroll', handleReveal);
  
  // 数字动画
  setTimeout(() => {
    animateCounter(document.getElementById('stat-samples'), 10000);
    animateCounter(document.getElementById('stat-factors'), 11);
    animateCounter(document.getElementById('stat-r2'), 92);
    animateCounter(document.getElementById('stat-accuracy'), 89);
    
    // 进度条动画
    animateProgress();
  }, 500);
  
  // 导出报告按钮
  const exportBtn = document.getElementById('export-report');
  if (exportBtn) {
    exportBtn.addEventListener('click', () => {
      alert('报告导出功能正在开发中，敬请期待！');
    });
  }
});
