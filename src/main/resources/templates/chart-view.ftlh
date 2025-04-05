<!DOCTYPE HTML>
<html>
<head>
    <script type="text/javascript">
        window.onload = function () {

            var chart = new CanvasJS.Chart("chartContainer", {
                theme: "light1", // "light2", "dark1", "dark2"
                animationEnabled: false, // change to true
                title:{
                    text: "Count of sold products by months"
                },
                data: [
                    {
                        type: "column",
                        dataPoints: [
                            <#list statistics as month, quantity>
                                { label: "${month}",  y: ${quantity}  },
                            </#list>
                        ]
                    }
                ]
            });
            chart.render();

        }
    </script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://cdn.canvasjs.com/canvasjs.min.js"> </script>
<button>
    <a href="/">View as a table</a>
</button>
</body>
</html>