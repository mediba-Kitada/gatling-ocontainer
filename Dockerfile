# 公式イメージを利用
## JDK 8u92
## OS Alpine Linux
FROM java:8u92-jdk-alpine

# 開発チームの連絡先
MAINTAINER mediba-ktiada <kitada@mediba.jp>

# gatlingのバージョンを指定
ENV GATLING_VERSION=2.2.2 \
# 攻撃対象の環境
URL=https://samp.lu \
# エンドポイント
TARGET=/hoge \
# cookie: hoge
hoge=ThisisCookie \
# 攻撃量(毎秒リクエスト数)
PS=5 \
# 攻撃時間(秒)
LT=60

# gatlingをインストール、セットアップ
RUN set -ex \
    && wget -O ./gatling.zip http://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/$GATLING_VERSION/gatling-charts-highcharts-bundle-$GATLING_VERSION-bundle.zip \
	&& unzip gatling.zip \
	&& mv ./gatling-charts-highcharts-bundle-$GATLING_VERSION gatling \
	&& mkdir -p ./gatling/user-files/simulations/hoge \
	&& rm -rf ./gatling/user-files/simulations/computerdatabase

# 攻撃用コードを配置
COPY ./hoge/get.scala ./gatling/user-files/simulations/hoge

CMD ["./gatling/bin/gatling.sh"]
