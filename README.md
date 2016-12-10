gatlingおコンテナ
====

- gatlingを用いて性能試験(WebAPIへの攻撃)を実施する
- Dockerを用いて移植性に特化した環境構築を指向
	- 軽量なコンテナを指向

```
% tree ./
./
├── Dockerfile
├── README.md
└── hoge # リソース別にパッケージを作成 hogeパッケージ
	└──── get.scala # リクエスト別にクラスを作成 Getクラス
```
## Install

- requirements
	- Docker for Mac
- Dockerイメージをビルドする

```
% cd /path/to/gatling-ocontainer
% docker build -t gatling .
```

## Usage

- Dockerコンテナを起動させ、ttyで攻撃を指令
	- 攻撃対象の環境やエンドポイント、攻撃量、攻撃時間は、環境変数にセットする(Dockerfile参照)
	- 環境変数経由で攻撃毎にパラメータを設定可能となる

```
# https://samp.lu/hoge に対して、10req/secの攻撃を10秒間実施
% docker run -ti -e URL=https://samp.lu -e TARGET=/hoge -e PS=10 -e LT=10 gatling
GATLING_HOME is set to /gatling

12:59:15.885 [WARN ] i.g.c.ZincCompiler$ - Pruning sources from previous analysis, due to incompatible CompileSetup.

# レポートファイルのID 空でよいのでenterを押下
Select simulation id (default is 'allpostnew'). Accepted characters are a-z, A-Z, 0-9, - and _

# レポートファイルの説明 空でよいのでenterを押下
Select run description (optional)

# 攻撃開始
Simulation hoge.Get started...
中略

================================================================================
---- Global Information --------------------------------------------------------
> request count                                        330 (OK=329    KO=1     )
> min response time                                    137 (OK=137    KO=11022 )
> max response time                                  11022 (OK=1171   KO=11022 )
> mean response time                                   256 (OK=223    KO=11022 )
> std deviation                                        607 (OK=130    KO=0     )
> response time 50th percentile                        174 (OK=174    KO=11022 )
> response time 75th percentile                        242 (OK=242    KO=11022 )
> response time 95th percentile                        425 (OK=423    KO=11022 )
> response time 99th percentile                       1002 (OK=781    KO=11022 )
> mean requests/sec                                  4.714 (OK=4.7    KO=0.014 )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                           326 ( 99%)
> 800 ms < t < 1200 ms                                   3 (  1%)
> t > 1200 ms                                            0 (  0%)
> failed                                                 1 (  0%)
---- Errors --------------------------------------------------------------------
> j.n.ConnectException: handshake timed out                           1 (100.0%)
================================================================================
```
