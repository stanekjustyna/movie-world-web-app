function add_rating(user_id, movie_id, rating) {
            $(function () {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            });

            $.ajax({
                 type: 'POST',
                 url: '/collect/rating/',
                 dataType: 'json',
                 contentType: "application/json; charset=utf-8",
                 data: JSON.stringify ({
                        "userId": user_id,
                        "movieId": movie_id,
                        "rating": rating
                        }),
                 fail: function(){
                     console.log('log failed(' + add_rating + ')')
                    }
            })};

function add_to_list(user_id, movie_id) {
            $(function () {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            });
            $.ajax({
                 type: 'POST',
                 url: '/collect/list/',
                 dataType: 'json',
                 contentType: "application/json; charset=utf-8",
                 data: JSON.stringify ({
                        "userId": user_id,
                        "movieId": movie_id
                        }),
                 fail: function(){
                     console.log('log failed(' + add_to_list + ')')
                    }
            })};

function add_event(user_id, movie_id, event_type, genre) {
            $(function () {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            });
            $.ajax({
                 type: 'POST',
                 url: '/collect/event/',
                 dataType: 'json',
                 contentType: "application/json; charset=utf-8",
                 data: JSON.stringify ({
                        "userId": user_id,
                        "movieId": movie_id,
                        "eventType": event_type,
                        "genre": genre
                        }),
                 fail: function(){
                     console.log('log failed(' + add_event + ')')
                    }
            })};