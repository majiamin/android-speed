package com.speed.faster.bean;

import java.io.Serializable;
import java.util.List;

public class AppManagerBean implements Serializable {

    /**
     * app : {"id":139,"name":"极速加速器","auditMode":true,"meta":{"QQ":"11111111","buyUrl":"http://www.dingniuit.com/payalb/index.html","update":{"title":"极速加速器","message":"更稳定，更快速，更高效！","versionCode":1},"recommond":{"type":"google","apppkg":"com.test","isShow":true,"imageurl":"https://tjiamge.oss-cn-beijing.aliyuncs.com/sd_iphone.jpg"},"isShowRate":true}}
     * ads : {"SPLASH":{"id":1646,"name":"开屏","position":"SPLASH","showType":["baidu"],"enable":true,"baidu":{"aid":"d65f0441","pid":"3906801"},"google":null,"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"HOME_INTER":{"id":1647,"name":"首页插页","position":"HOME_INTER","showType":["custom"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4025377"},"google":{"pid":"ca-app-pub-2526190456089841/5176590012"},"chartbox":{"pid":"HOME_INTER"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":{"portrait":"https://book.hizuoye.com/pages/hz_tj/","landscape":"https://book.hizuoye.com/pages/hz_tj/"},"meta":null},"REACTIVE":{"id":1648,"name":"唤醒","position":"REACTIVE","showType":["google"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4096060"},"google":{"pid":"ca-app-pub-2526190456089841/8130056414"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"SWITCHNODES":{"id":1649,"name":"切换节点","position":"SWITCHNODES","showType":["google"],"enable":false,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/3560256012"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"BACK_HOME":{"id":1650,"name":"返回首页","position":"BACK_HOME","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-9269017629001006/3831031174"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"START":{"id":1651,"name":"开启","position":"START","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/2083522813"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"BANNER":{"id":1652,"name":"横幅","position":"BANNER","showType":["google"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4096817"},"google":{"pid":"ca-app-pub-2526190456089841/6653323218"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"BANNER_FAIL":{"id":1653,"name":"banner失败","position":"BANNER_FAIL","showType":["baidu"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4097252"},"google":null,"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"NATIVE":{"id":1654,"name":"GOOGLE原生","position":"NATIVE","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/6710905211"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"GOOGLE_FAIL":{"id":1655,"name":"google失败","position":"GOOGLE_FAIL","showType":["baidu"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4080412"},"google":null,"chartbox":{"pid":"GOOGLE_FAIL"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null},"REWORDSVIDEO":{"id":1656,"name":"激励视频","position":"REWORDSVIDEO","showType":["chartbox"],"enable":true,"baidu":null,"google":null,"chartbox":{"pid":"REWORDSVIDEO"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":{"type":"inter"}},"TASKREWORD":{"id":1657,"name":"任务奖励","position":"TASKREWORD","showType":["chartbox","inmobi","vungle"],"enable":true,"baidu":null,"google":null,"chartbox":{"pid":"TASKREWORD"},"gdt":null,"inmobi":{"aid":"737b000f1cf54d809b0ecd865a10825f","pid":"1502716967718"},"vungle":{"aid":"5971b6f6bbddebc95e002eb5","pid":"DEFAULT06616"},"jusha":null,"custom":null,"meta":{"type":"video"}},"CHARTBOOST":{"id":1658,"name":"chartboost","position":"CHARTBOOST","showType":["chartbox"],"enable":true,"baidu":null,"google":null,"chartbox":{"aid":"59377f3204b016076a072065","pid":"269a3f0f9d71b4e0e5a2980b62fcbe92418318b3"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}}
     */
    private AppBean app;
    private AdsBean ads;

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public AdsBean getAds() {
        return ads;
    }

    public void setAds(AdsBean ads) {
        this.ads = ads;
    }

    public static class AppBean implements Serializable {
        /**
         * id : 139
         * name : 极速加速器
         * auditMode : true
         * meta : {"QQ":"11111111","buyUrl":"http://www.dingniuit.com/payalb/index.html","update":{"title":"极速加速器","message":"更稳定，更快速，更高效！","versionCode":1},"recommond":{"type":"google","apppkg":"com.test","isShow":true,"imageurl":"https://tjiamge.oss-cn-beijing.aliyuncs.com/sd_iphone.jpg"},"isShowRate":true}
         */

        private int id;
        private String name;
        private boolean auditMode;
        private MetaBean meta;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAuditMode() {
            return auditMode;
        }

        public void setAuditMode(boolean auditMode) {
            this.auditMode = auditMode;
        }

        public MetaBean getMeta() {
            return meta;
        }

        public void setMeta(MetaBean meta) {
            this.meta = meta;
        }

        public static class MetaBean implements Serializable {
            /**
             * QQ : 11111111
             * buyUrl : http://www.dingniuit.com/payalb/index.html
             * update : {"title":"极速加速器","message":"更稳定，更快速，更高效！","versionCode":1}
             * recommond : {"type":"google","apppkg":"com.test","isShow":true,"imageurl":"https://tjiamge.oss-cn-beijing.aliyuncs.com/sd_iphone.jpg"}
             * isShowRate : true
             */

            private String QQ;
            private String buyUrl;
            private UpdateBean update;
            private RecommondBean recommond;
            private boolean isShowRate;

            public String getQQ() {
                return QQ;
            }

            public void setQQ(String QQ) {
                this.QQ = QQ;
            }

            public String getBuyUrl() {
                return buyUrl;
            }

            public void setBuyUrl(String buyUrl) {
                this.buyUrl = buyUrl;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public RecommondBean getRecommond() {
                return recommond;
            }

            public void setRecommond(RecommondBean recommond) {
                this.recommond = recommond;
            }

            public boolean isIsShowRate() {
                return isShowRate;
            }

            public void setIsShowRate(boolean isShowRate) {
                this.isShowRate = isShowRate;
            }

            public static class UpdateBean implements Serializable {
                /**
                 * url:"",
                 *title:极速加速器,
                 *message:更稳定，更快速，更高效！,
                 *versionCode:1,
                 *versionName:1.0.1
                 */

                private String title;
                private String message;
                private int versionCode;
                private String versionName;
                private String url;

                public String getVersionName() {
                    return versionName;
                }

                public void setVersionName(String versionName) {
                    this.versionName = versionName;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public int getVersionCode() {
                    return versionCode;
                }

                public void setVersionCode(int versionCode) {
                    this.versionCode = versionCode;
                }
            }

            public static class RecommondBean implements Serializable {
                /**
                 * type : google
                 * apppkg : com.test
                 * isShow : true
                 * imageurl : https://tjiamge.oss-cn-beijing.aliyuncs.com/sd_iphone.jpg
                 */

                private String type;
                private String apppkg;
                private boolean isShow;
                private String imageurl;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getApppkg() {
                    return apppkg;
                }

                public void setApppkg(String apppkg) {
                    this.apppkg = apppkg;
                }

                public boolean isIsShow() {
                    return isShow;
                }

                public void setIsShow(boolean isShow) {
                    this.isShow = isShow;
                }

                public String getImageurl() {
                    return imageurl;
                }

                public void setImageurl(String imageurl) {
                    this.imageurl = imageurl;
                }
            }
        }
    }

    public static class AdsBean implements Serializable {
        /**
         * SPLASH : {"id":1646,"name":"开屏","position":"SPLASH","showType":["baidu"],"enable":true,"baidu":{"aid":"d65f0441","pid":"3906801"},"google":null,"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * HOME_INTER : {"id":1647,"name":"首页插页","position":"HOME_INTER","showType":["custom"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4025377"},"google":{"pid":"ca-app-pub-2526190456089841/5176590012"},"chartbox":{"pid":"HOME_INTER"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":{"portrait":"https://book.hizuoye.com/pages/hz_tj/","landscape":"https://book.hizuoye.com/pages/hz_tj/"},"meta":null}
         * REACTIVE : {"id":1648,"name":"唤醒","position":"REACTIVE","showType":["google"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4096060"},"google":{"pid":"ca-app-pub-2526190456089841/8130056414"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * SWITCHNODES : {"id":1649,"name":"切换节点","position":"SWITCHNODES","showType":["google"],"enable":false,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/3560256012"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * BACK_HOME : {"id":1650,"name":"返回首页","position":"BACK_HOME","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-9269017629001006/3831031174"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * START : {"id":1651,"name":"开启","position":"START","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/2083522813"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * BANNER : {"id":1652,"name":"横幅","position":"BANNER","showType":["google"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4096817"},"google":{"pid":"ca-app-pub-2526190456089841/6653323218"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * BANNER_FAIL : {"id":1653,"name":"banner失败","position":"BANNER_FAIL","showType":["baidu"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4097252"},"google":null,"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * NATIVE : {"id":1654,"name":"GOOGLE原生","position":"NATIVE","showType":["google"],"enable":true,"baidu":null,"google":{"pid":"ca-app-pub-2526190456089841/6710905211"},"chartbox":null,"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * GOOGLE_FAIL : {"id":1655,"name":"google失败","position":"GOOGLE_FAIL","showType":["baidu"],"enable":true,"baidu":{"aid":"b6b925d8","pid":"4080412"},"google":null,"chartbox":{"pid":"GOOGLE_FAIL"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         * REWORDSVIDEO : {"id":1656,"name":"激励视频","position":"REWORDSVIDEO","showType":["chartbox"],"enable":true,"baidu":null,"google":null,"chartbox":{"pid":"REWORDSVIDEO"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":{"type":"inter"}}
         * TASKREWORD : {"id":1657,"name":"任务奖励","position":"TASKREWORD","showType":["chartbox","inmobi","vungle"],"enable":true,"baidu":null,"google":null,"chartbox":{"pid":"TASKREWORD"},"gdt":null,"inmobi":{"aid":"737b000f1cf54d809b0ecd865a10825f","pid":"1502716967718"},"vungle":{"aid":"5971b6f6bbddebc95e002eb5","pid":"DEFAULT06616"},"jusha":null,"custom":null,"meta":{"type":"video"}}
         * CHARTBOOST : {"id":1658,"name":"chartboost","position":"CHARTBOOST","showType":["chartbox"],"enable":true,"baidu":null,"google":null,"chartbox":{"aid":"59377f3204b016076a072065","pid":"269a3f0f9d71b4e0e5a2980b62fcbe92418318b3"},"gdt":null,"inmobi":null,"vungle":null,"jusha":null,"custom":null,"meta":null}
         */

        private SPLASHBean SPLASH;
        private HOMEINTERBean HOME_INTER;
        private REACTIVEBean REACTIVE;
        private SWITCHNODESBean SWITCHNODES;
        private BACKHOMEBean BACK_HOME;
        private STARTBean START;
        private BANNERBean BANNER;
        private BANNERFAILBean BANNER_FAIL;
        private NATIVEBean NATIVE;
        private GOOGLEFAILBean GOOGLE_FAIL;
        private REWORDSVIDEOBean REWORDSVIDEO;
        private TASKREWORDBean TASKREWORD;
        private CHARTBOOSTBean CHARTBOOST;

        public SPLASHBean getSPLASH() {
            return SPLASH;
        }

        public void setSPLASH(SPLASHBean SPLASH) {
            this.SPLASH = SPLASH;
        }

        public HOMEINTERBean getHOME_INTER() {
            return HOME_INTER;
        }

        public void setHOME_INTER(HOMEINTERBean HOME_INTER) {
            this.HOME_INTER = HOME_INTER;
        }

        public REACTIVEBean getREACTIVE() {
            return REACTIVE;
        }

        public void setREACTIVE(REACTIVEBean REACTIVE) {
            this.REACTIVE = REACTIVE;
        }

        public SWITCHNODESBean getSWITCHNODES() {
            return SWITCHNODES;
        }

        public void setSWITCHNODES(SWITCHNODESBean SWITCHNODES) {
            this.SWITCHNODES = SWITCHNODES;
        }

        public BACKHOMEBean getBACK_HOME() {
            return BACK_HOME;
        }

        public void setBACK_HOME(BACKHOMEBean BACK_HOME) {
            this.BACK_HOME = BACK_HOME;
        }

        public STARTBean getSTART() {
            return START;
        }

        public void setSTART(STARTBean START) {
            this.START = START;
        }

        public BANNERBean getBANNER() {
            return BANNER;
        }

        public void setBANNER(BANNERBean BANNER) {
            this.BANNER = BANNER;
        }

        public BANNERFAILBean getBANNER_FAIL() {
            return BANNER_FAIL;
        }

        public void setBANNER_FAIL(BANNERFAILBean BANNER_FAIL) {
            this.BANNER_FAIL = BANNER_FAIL;
        }

        public NATIVEBean getNATIVE() {
            return NATIVE;
        }

        public void setNATIVE(NATIVEBean NATIVE) {
            this.NATIVE = NATIVE;
        }

        public GOOGLEFAILBean getGOOGLE_FAIL() {
            return GOOGLE_FAIL;
        }

        public void setGOOGLE_FAIL(GOOGLEFAILBean GOOGLE_FAIL) {
            this.GOOGLE_FAIL = GOOGLE_FAIL;
        }

        public REWORDSVIDEOBean getREWORDSVIDEO() {
            return REWORDSVIDEO;
        }

        public void setREWORDSVIDEO(REWORDSVIDEOBean REWORDSVIDEO) {
            this.REWORDSVIDEO = REWORDSVIDEO;
        }

        public TASKREWORDBean getTASKREWORD() {
            return TASKREWORD;
        }

        public void setTASKREWORD(TASKREWORDBean TASKREWORD) {
            this.TASKREWORD = TASKREWORD;
        }

        public CHARTBOOSTBean getCHARTBOOST() {
            return CHARTBOOST;
        }

        public void setCHARTBOOST(CHARTBOOSTBean CHARTBOOST) {
            this.CHARTBOOST = CHARTBOOST;
        }

        public static class SPLASHBean implements Serializable {
            /**
             * id : 1646
             * name : 开屏
             * position : SPLASH
             * showType : ["baidu"]
             * enable : true
             * baidu : {"aid":"d65f0441","pid":"3906801"}
             * google : null
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBean baidu;
            private Object google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBean getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBean baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBean implements Serializable {
                /**
                 * aid : d65f0441
                 * pid : 3906801
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class HOMEINTERBean implements Serializable {
            /**
             * id : 1647
             * name : 首页插页
             * position : HOME_INTER
             * showType qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq: ["custom"]
             * enable : true
             * baidu : {"aid":"b6b925d8","pid":"4025377"}
             * google : {"pid":"ca-app-pub-2526190456089841/5176590012"}
             * chartbox : {"pid":"HOME_INTER"}
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : {"portrait":"https://book.hizuoye.com/pages/hz_tj/","landscape":"https://book.hizuoye.com/pages/hz_tj/"}
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBeanX baidu;
            private GoogleBean google;
            private ChartboxBean chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private CustomBean custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBeanX getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBeanX baidu) {
                this.baidu = baidu;
            }

            public GoogleBean getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBean google) {
                this.google = google;
            }

            public ChartboxBean getChartbox() {
                return chartbox;
            }

            public void setChartbox(ChartboxBean chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public CustomBean getCustom() {
                return custom;
            }

            public void setCustom(CustomBean custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBeanX implements Serializable {
                /**
                 * aid : b6b925d8
                 * pid : 4025377
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class GoogleBean implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/5176590012
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class ChartboxBean implements Serializable {
                /**
                 * pid : HOME_INTER
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class CustomBean implements Serializable {
                /**
                 * portrait : https://book.hizuoye.com/pages/hz_tj/
                 * landscape : https://book.hizuoye.com/pages/hz_tj/
                 */

                private String portrait;
                private String landscape;

                public String getPortrait() {
                    return portrait;
                }

                public void setPortrait(String portrait) {
                    this.portrait = portrait;
                }

                public String getLandscape() {
                    return landscape;
                }

                public void setLandscape(String landscape) {
                    this.landscape = landscape;
                }
            }
        }

        public static class REACTIVEBean implements Serializable {
            /**
             * id : 1648
             * name : 唤醒
             * position : REACTIVE
             * showType : ["google"]
             * enable : true
             * baidu : {"aid":"b6b925d8","pid":"4096060"}
             * google : {"pid":"ca-app-pub-2526190456089841/8130056414"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBeanXX baidu;
            private GoogleBeanX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBeanXX getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBeanXX baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBeanXX implements Serializable {
                /**
                 * aid : b6b925d8
                 * pid : 4096060
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class GoogleBeanX implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/8130056414
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class SWITCHNODESBean implements Serializable {
            /**
             * id : 1649
             * name : 切换节点
             * position : SWITCHNODES
             * showType : ["google"]
             * enable : false
             * baidu : null
             * google : {"pid":"ca-app-pub-2526190456089841/3560256012"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private GoogleBeanXX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanXX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanXX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class GoogleBeanXX implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/3560256012
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class BACKHOMEBean implements Serializable {
            /**
             * id : 1650
             * name : 返回首页
             * position : BACK_HOME
             * showType : ["google"]
             * enable : true
             * baidu : null
             * google : {"pid":"ca-app-pub-9269017629001006/3831031174"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private GoogleBeanXXX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanXXX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanXXX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class GoogleBeanXXX implements Serializable {
                /**
                 * pid : ca-app-pub-9269017629001006/3831031174
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class STARTBean implements Serializable {
            /**
             * id : 1651
             * name : 开启
             * position : START
             * showType : ["google"]
             * enable : true
             * baidu : null
             * google : {"pid":"ca-app-pub-2526190456089841/2083522813"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private GoogleBeanXXXX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanXXXX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanXXXX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class GoogleBeanXXXX implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/2083522813
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class BANNERBean implements Serializable {
            /**
             * id : 1652
             * name : 横幅
             * position : BANNER
             * showType : ["google"]
             * enable : true
             * baidu : {"aid":"b6b925d8","pid":"4096817"}
             * google : {"pid":"ca-app-pub-2526190456089841/6653323218"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBeanXXX baidu;
            private GoogleBeanXXXXX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBeanXXX getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBeanXXX baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanXXXXX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanXXXXX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBeanXXX implements Serializable {
                /**
                 * aid : b6b925d8
                 * pid : 4096817
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class GoogleBeanXXXXX implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/6653323218
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class BANNERFAILBean implements Serializable {
            /**
             * id : 1653
             * name : banner失败
             * position : BANNER_FAIL
             * showType : ["baidu"]
             * enable : true
             * baidu : {"aid":"b6b925d8","pid":"4097252"}
             * google : null
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBeanXXXX baidu;
            private Object google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBeanXXXX getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBeanXXXX baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBeanXXXX implements Serializable {
                /**
                 * aid : b6b925d8
                 * pid : 4097252
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class NATIVEBean implements Serializable {
            /**
             * id : 1654
             * name : GOOGLE原生
             * position : NATIVE
             * showType : ["google"]
             * enable : true
             * baidu : null
             * google : {"pid":"ca-app-pub-2526190456089841/6710905211"}
             * chartbox : null
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private GoogleBeanXXXXXX google;
            private Object chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public GoogleBeanXXXXXX getGoogle() {
                return google;
            }

            public void setGoogle(GoogleBeanXXXXXX google) {
                this.google = google;
            }

            public Object getChartbox() {
                return chartbox;
            }

            public void setChartbox(Object chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class GoogleBeanXXXXXX implements Serializable {
                /**
                 * pid : ca-app-pub-2526190456089841/6710905211
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class GOOGLEFAILBean implements Serializable {
            /**
             * id : 1655
             * name : google失败
             * position : GOOGLE_FAIL
             * showType : ["baidu"]
             * enable : true
             * baidu : {"aid":"b6b925d8","pid":"4080412"}
             * google : null
             * chartbox : {"pid":"GOOGLE_FAIL"}
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private BaiduBeanXXXXX baidu;
            private Object google;
            private ChartboxBeanX chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public BaiduBeanXXXXX getBaidu() {
                return baidu;
            }

            public void setBaidu(BaiduBeanXXXXX baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public ChartboxBeanX getChartbox() {
                return chartbox;
            }

            public void setChartbox(ChartboxBeanX chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class BaiduBeanXXXXX implements Serializable {
                /**
                 * aid : b6b925d8
                 * pid : 4080412
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class ChartboxBeanX implements Serializable {
                /**
                 * pid : GOOGLE_FAIL
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }

        public static class REWORDSVIDEOBean implements Serializable {
            /**
             * id : 1656
             * name : 激励视频
             * position : REWORDSVIDEO
             * showType : ["chartbox"]
             * enable : true
             * baidu : null
             * google : null
             * chartbox : {"pid":"REWORDSVIDEO"}
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : {"type":"inter"}
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private Object google;
            private ChartboxBeanXX chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private MetaBeanX meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public ChartboxBeanXX getChartbox() {
                return chartbox;
            }

            public void setChartbox(ChartboxBeanXX chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public MetaBeanX getMeta() {
                return meta;
            }

            public void setMeta(MetaBeanX meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class ChartboxBeanXX implements Serializable {
                /**
                 * pid : REWORDSVIDEO
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class MetaBeanX implements Serializable {
                /**
                 * type : inter
                 */

                private String type;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class TASKREWORDBean implements Serializable {
            /**
             * id : 1657
             * name : 任务奖励
             * position : TASKREWORD
             * showType : ["chartbox","inmobi","vungle"]
             * enable : true
             * baidu : null
             * google : null
             * chartbox : {"pid":"TASKREWORD"}
             * gdt : null
             * inmobi : {"aid":"737b000f1cf54d809b0ecd865a10825f","pid":"1502716967718"}
             * vungle : {"aid":"5971b6f6bbddebc95e002eb5","pid":"DEFAULT06616"}
             * jusha : null
             * custom : null
             * meta : {"type":"video"}
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private Object google;
            private ChartboxBeanXXX chartbox;
            private Object gdt;
            private InmobiBean inmobi;
            private VungleBean vungle;
            private Object jusha;
            private Object custom;
            private MetaBeanXX meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public ChartboxBeanXXX getChartbox() {
                return chartbox;
            }

            public void setChartbox(ChartboxBeanXXX chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public InmobiBean getInmobi() {
                return inmobi;
            }

            public void setInmobi(InmobiBean inmobi) {
                this.inmobi = inmobi;
            }

            public VungleBean getVungle() {
                return vungle;
            }

            public void setVungle(VungleBean vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public MetaBeanXX getMeta() {
                return meta;
            }

            public void setMeta(MetaBeanXX meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class ChartboxBeanXXX implements Serializable {
                /**
                 * pid : TASKREWORD
                 */

                private String pid;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class InmobiBean implements Serializable {
                /**
                 * aid : 737b000f1cf54d809b0ecd865a10825f
                 * pid : 1502716967718
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class VungleBean implements Serializable {
                /**
                 * aid : 5971b6f6bbddebc95e002eb5
                 * pid : DEFAULT06616
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }

            public static class MetaBeanXX implements Serializable {
                /**
                 * type : video
                 */

                private String type;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class CHARTBOOSTBean implements Serializable {
            /**
             * id : 1658
             * name : chartboost
             * position : CHARTBOOST
             * showType : ["chartbox"]
             * enable : true
             * baidu : null
             * google : null
             * chartbox : {"aid":"59377f3204b016076a072065","pid":"269a3f0f9d71b4e0e5a2980b62fcbe92418318b3"}
             * gdt : null
             * inmobi : null
             * vungle : null
             * jusha : null
             * custom : null
             * meta : null
             */

            private int id;
            private String name;
            private String position;
            private boolean enable;
            private Object baidu;
            private Object google;
            private ChartboxBeanXXXX chartbox;
            private Object gdt;
            private Object inmobi;
            private Object vungle;
            private Object jusha;
            private Object custom;
            private Object meta;
            private List<String> showType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public Object getBaidu() {
                return baidu;
            }

            public void setBaidu(Object baidu) {
                this.baidu = baidu;
            }

            public Object getGoogle() {
                return google;
            }

            public void setGoogle(Object google) {
                this.google = google;
            }

            public ChartboxBeanXXXX getChartbox() {
                return chartbox;
            }

            public void setChartbox(ChartboxBeanXXXX chartbox) {
                this.chartbox = chartbox;
            }

            public Object getGdt() {
                return gdt;
            }

            public void setGdt(Object gdt) {
                this.gdt = gdt;
            }

            public Object getInmobi() {
                return inmobi;
            }

            public void setInmobi(Object inmobi) {
                this.inmobi = inmobi;
            }

            public Object getVungle() {
                return vungle;
            }

            public void setVungle(Object vungle) {
                this.vungle = vungle;
            }

            public Object getJusha() {
                return jusha;
            }

            public void setJusha(Object jusha) {
                this.jusha = jusha;
            }

            public Object getCustom() {
                return custom;
            }

            public void setCustom(Object custom) {
                this.custom = custom;
            }

            public Object getMeta() {
                return meta;
            }

            public void setMeta(Object meta) {
                this.meta = meta;
            }

            public List<String> getShowType() {
                return showType;
            }

            public void setShowType(List<String> showType) {
                this.showType = showType;
            }

            public static class ChartboxBeanXXXX implements Serializable {
                /**
                 * aid : 59377f3204b016076a072065
                 * pid : 269a3f0f9d71b4e0e5a2980b62fcbe92418318b3
                 */

                private String aid;
                private String pid;

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }
    }
}
