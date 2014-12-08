$(document).ready(function(){
	var stations = [];
	var clickTime = 0;
	$.each(station_names.split('@'), function(i, item){
		if(item != ''){
			var names = item.split('|');
			stations.push({
				key: '|' + names[3] + '|' + names[4],
				name: names[1],
				skey: names[2]
			});
		}
	});
	$('.station').bind('keyup', function(){
		var key = $(this).val();
		var last = $(this).attr('last');
		if(key == last){
			return;
		}
		$(this).attr('last', key);
		var ul = $(this).next();
		ul.html('');
		var candicates = [];
		for(var i = 0; i < stations.length; i++){
			if(stations[i].key.indexOf('|' + key) > -1){
				ul.append('<li><a data-index="' + i + '" href="javascript:;">' + stations[i].name + '</a></li>');
			}
		}
		ul.addClass('show');
	});
	$('body').on('click', '.station-select a', function(){
		console.log('clicked');
		var i = parseInt($(this).data('index'));
		if(!isNaN(i)){
			var input = $(this).parent().parent().prev();
			input.val(stations[i].name);
			$('#' + input.data('id')).val(stations[i].skey);
		}
		$(this).parent().parent().removeClass('show');
		$('.station').attr('last', '');
	});
	$('#query').click(function(){
		if(isValid()){
			clickTime = new Date().getTime();
			$('.table-list').html('');
			query(clickTime);
		}
	});
	function query(_clickTime, date){
		$.ajax({
			url: ctx + '/query',
			data: {
				from: $('#from').val(),
				to: $('#to').val(),
				date: date?date:''
			},
			dataType: 'json',
			success: function(data){
				if(data.data.data.datas){
					var result = $('<table class="result" cellspacing="0" cellpadding="0"></table>');
					result.append('<caption>' + data.current + '</caption>');
					result.append(
						'<tr>' +
							'<td>车次</td>' + 
							'<td>出发站</td>' + 
							'<td>到达站</td>' + 
							'<td>历时</td>' + 
							'<td>商务座</td>' + 
							'<td>特等座</td>' + 
							'<td>一等座</td>' + 
							'<td>二等座</td>' + 
							'<td>高级软卧</td>' + 
							'<td>软卧</td>' + 
							'<td>硬卧</td>' + 
							'<td>软座</td>' + 
							'<td>硬座</td>' + 
							'<td>无座</td>' + 
							'<td>其他</td>' + 
						'</tr>'
					);
					$.each(data.data.data.datas, function(i, item){
						result.append(
							'<tr>' +
								'<td>' + item.station_train_code+ '</td>' + 
								'<td>' + item.from_station_name + '-' + item.start_time + '</td>' + 
								'<td>' + item.to_station_name + '-' + item.arrive_time + '</td>' + 
								'<td>' + item.lishi + '</td>' + 
								'<td>' + item.swz_num + '</td>' + 
								'<td>' + item.tz_num + '</td>' + 
								'<td>' + item.zy_num + '</td>' + 
								'<td>' + item.ze_num + '</td>' + 
								'<td>' + item.gr_num + '</td>' + 
								'<td>' + item.rw_num + '</td>' + 
								'<td>' + item.yw_num + '</td>' + 
								'<td>' + item.rz_num + '</td>' + 
								'<td>' + item.yz_num + '</td>' + 
								'<td>' + item.wz_num + '</td>' + 
								'<td>' + item.qt_num + '</td>' + 
							'</tr>'
						);
					});
					if(clickTime == _clickTime){
						$('.table-list').append(result);
						if(data.next){
							setTimeout(function(){
								query(_clickTime, data.next);
							}, 300);
						}
					}
				}
			}
		});
	}
	$('#chart').click(function(){
		if(isValid()){
			$('.head').hide();
			clickTime = new Date().getTime();
			var _clickTime = clickTime;
			$('.table-list').html('');
			var now = new Date();
			now.setDate(now.getDate()+20);
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			var date = now.getDate();
			$.ajax({
				url: ctx + '/query',
				type: 'POST',
				dataType: 'json',
				data: {
					from: $('#from').val(),
					to: $('#to').val(),
					date: year + '-' + (month<10?'0':'') + month + '-' + (date<10?'0':'') + date
				},
				success: function(data){
					if(data.data.data.datas){
						if(clickTime == _clickTime){
							createCharts(data.data.data.datas, _clickTime);
						}
					}
				}
			});
			function createCharts(datas, _clickTime){
				$.each(datas, function(i, item){
					var id = new Date().getTime();
					var chartWrapper = $('<div id="chartDiv-' + id + '" class="chart"></div>').appendTo($('.table-list'));
					window[item.station_train_code] = new FusionCharts(ctx + "/js/Charts/RealTimeLine.swf", "chart-" + id, "100%", "500", "0", "0");
					window[item.station_train_code].setDataURL(ctx + '/chart-data?code=' + item.station_train_code + '&from=' + $('#from').val() + '&to=' + $('#to').val());
					window[item.station_train_code].render("chartDiv-" + id);
				});
				var i = 0;
				update();
				function update(){
					$.ajax({
						url: ctx + '/real-data',
						type: 'POST',
						dataType: 'json',
						data: {
							from: $('#from').val(),
							to: $('#to').val(),
							days: i++
						},
						success: function(data){
							for(key in data){
								if(key != 'heihei' && window[key] && window[key].feedData){
									window[key].feedData(data[key]);
								}
							}
							setTimeout(function(){
								if(i < 60){
									update();
								}
								else {
									$('.head').show();
								}
							}, 200);
						}
					});
				}
			}
		}
	});
	function isValid(){
		return true;
	}
	$(document).bind('mousedown', function(e){
		if($(e.target).hasClass('station-select') || $(e.target).closest('.station-select').length > 0)
			return;
		var select = $('.station-select.show');
		if(select.length > 0){
			select.find('li a:first').trigger('click');
		}
	});
	$('.station').focus(function(){
		$(this).select();
	});
});