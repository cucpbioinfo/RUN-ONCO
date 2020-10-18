package run.onco.api.common.constants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface AppConstants {
	
	// root package of java code
	public static final String ROOT_PACKAGE = "run.onco.api";
	public static final String REST_API_PACKAGE = "run.onco.api.rest.controller";

	public static final String CULTURE_SHORTNAME_THAI = "TH";
	public static final String CULTURE_SHORTNAME_ENGLISH = "EN";

	public static final String KNOWN_CULTURE_ENLISH_US = "en-US";
	public static final String KNOWN_CULTURE_THAI = "th-TH";

	public static final int SCALE_TWO_DIGIT = 2;
	public static final String EMPTY_STRING = "";

	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";
	public static final String STATUS_DRAFT = "DRAFT";
	public static final String STATUS_PROCESSING ="PROCESSING";
	public static final String STATUS_WAIT_VARIANTS_ANNOTATED ="WAIT_VARIANTS_ANNOTATED";
	
	public static final String CSV_SEPARATOR = ",";
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	// Data Type
	public static final String DATA_TYPE_CLINICAL_DATA = "1";
	public static final String DATA_TYPE_PATIENT = "2";
	
	public static final String MOBILE_NO_PATTERN = "\\d{10}";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String DELIVERY_CHANNEL_PATTERN = "^([1-2]|1)$";
	public static final String REF_NO_PATTERN = "^ONC[0-9]{18}$";
	public static final String TXN_DATE_PATTERN = "^[0-9]{14}+$";
	public static final String ALLOW_ANYID_TYPE = "^[MSISDN|NATID]+$";
	public static final String DECIMAL_PATTERN = "^\\d{1}\\.\\d{1,2}$";
	public static final String ALLOW_CUST_TOKEN_TYPE = "^[M|C]+$";
	public static final String STORE_PROCEDURE_DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String CHROMOSOME_PATTERN = "(?:^chr)([a-zA-Z0-9]*)";
	public static final String MONGODB_URI_PATTERN = "mongodb:\\/\\/([^\\/]*)\\/[a-zA-Z0-9]*";
	
	public static final String SYSTEM_ONCO = "ONCO";
	
	public static final String SECURITY_CONTEXT = "sec";
	public static final String MASTER_DATA_MANAGEMENT_CONTEXT = "mdm";
	
	// Action Code
	public static final String ACT_CODE_GET_APP_VERSION = "getAppVersion";
	public static final String ACT_CODE_LOGIN = "login";
	public static final String ACT_CODE_GET_GENDER_LIST = "getGenderList";
	public static final String ACT_CODE_GET_BLOOD_TYPE_LIST = "getBloodTypeList";
	public static final String ACT_CODE_GET_CELL_TYPE_LIST = "getCellTypeList";
	public static final String ACT_CODE_GET_CELL_LINE_TYPE_LIST = "getCellLineTypeList";
	public static final String ACT_CODE_GET_FREEZE_METHOD_LIST = "getFreezeMethodList";
	public static final String ACT_CODE_GET_PRESERVE_METHOD_LIST = "getPreserveMethodList";
	public static final String ACT_CODE_GET_RACE_LIST = "getRaceList";
	public static final String ACT_CODE_GET_SAMPLE_TYPE_LIST = "getSampleTypeList";
	public static final String ACT_CODE_GET_STAGE_TYPE_LIST = "getStageTypeList";
	public static final String ACT_CODE_GET_TISSUE_TYPE_LIST = "getTissueTypeList";
	public static final String ACT_CODE_GET_VITAL_STATUS_LIST = "getVitalStatusList";
	public static final String ACT_CODE_GET_TISSUE_PROCEDURE_LIST = "getTissueProcedureList";
	public static final String ACT_CODE_SAVE_CLINICAL_DATA = "saveClinicalData";
	public static final String ACT_CODE_GET_HISTOLOGIC_GRADE_LIST = "getHistologicGradeList";
	public static final String ACT_CODE_GET_AJCC_LIST = "getAjccList";
	public static final String ACT_CODE_GET_CT_STAGE_LIST = "getCtStageList";
	public static final String ACT_CODE_GET_CN_STAGE_LIST = "getCnStageList";
	public static final String ACT_CODE_GET_PT_STAGE_LIST = "getPtStageList";
	public static final String ACT_CODE_GET_PN_STAGE_LIST = "getPnStageList";
	public static final String ACT_CODE_GET_YPT_STAGE_LIST = "getYptStageList";
	public static final String ACT_CODE_GET_YPN_STAGE_LIST = "getYpnStageList";
	public static final String ACT_CODE_GET_M_STAGE_LIST = "getMStageList";
	public static final String ACT_CODE_GET_CLINICAL_STAGE_LIST = "getClinicalStageList";
	public static final String ACT_CODE_GET_PATHOLOGICAL_STAGE_LIST = "getPathoStageList";
	public static final String ACT_CODE_GET_RECURRENCE_STATUS_LIST = "getRecurrenceStatusList";
	public static final String ACT_CODE_SEARCH_CLINICAL_DATA = "searchClinicalData";
	public static final String ACT_CODE_GET_CLINICAL_DATA = "getClinicalData";
	public static final String ACT_CODE_SEARCH_PATIENT_BY_REF = "searchPatientByRef";
	public static final String ACT_CODE_DELETE_CLINICAL_DATA = "deleteClinicalData";
	public static final String ACT_CODE_FILTER_ICD = "filterIcd";
	public static final String ACT_CODE_FILTER_ICD_O = "filterIcdO";
	public static final String ACT_CODE_SEARCH_ICD = "searchIcd";
	public static final String ACT_CODE_SEARCH_ICD_O = "searchIcdO";
	public static final String ACT_CODE_GEN_VIS_R_WATERFALL = "ACT_GEN_VIS_R_WATERFALL";
	public static final String ACT_CODE_GET_SEQ_TYPE_LIST = "getSeqTypeList";
	public static final String ACT_CODE_GET_SAMPLE_SOURCE_LIST = "getSampleSourceList";
	public static final String ACT_CODE_GET_SAMPLE_VCF_LIST_BY_PATIENT = "getSampleVcfListByPatient";
	public static final String ACT_CODE_DELETE_VARIANT_CALL = "deleteVariantCall";
	public static final String ACT_CODE_GET_VARIANT_CALL = "getVariantCall";
	public static final String ACT_CODE_SEARCH_PATIENT = "searchPatient";
	public static final String ACT_CODE_SEARCH_BIOSPECIMEN = "searchBiospecimen";
	public static final String ACT_CODE_GET_CANCER_SUBSTAGE_LIST = "getCancerSubstageList";
	public static final String ACT_CODE_GET_BIOSPECIMEN = "getBiospecimen";
	public static final String ACT_CODE_DELETE_BIOSPECIMEN = "deleteBiospecimen";
	public static final String ACT_CODE_SEARCH_PATIENT_PATHO_BY_PATHO_NO = "searchPatientPathoByPathoNo";
	public static final String ACT_CODE_SAVE_BIOSPECIMEN = "saveBiospecimen";
	public static final String ACT_CODE_SAVE_PATIENT = "savePatient";
	public static final String ACT_CODE_DELETE_PATIENT = "deletePatient";
	public static final String ACT_CODE_GET_PATIENT = "getPatient";
	public static final String ACT_CODE_UPLOAD_SAMPLE_VCF = "uploadSampleVcf";
	public static final String ACT_CODE_DELETE_RNA_SEQ = "deleteRnaSeq";
	public static final String ACT_CODE_UPLOAD_SAMPLE_RNA_SEQ = "uploadSampleRnaSeq";
	public static final String ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST = "getSampleRnaSeqList";
	public static final String ACT_CODE_GET_RNA_SEQ_LIST = "getRnaSeqList";
	public static final String ACT_CODE_GET_VARIANT_COMPARISON = "getVariantComparison";
	public static final String ACT_CODE_GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS = "getSampleVcfListByBiospecimens";
	public static final String ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS = "getSampleRnaSeqListByBiospecimens";
	public static final String ACT_CODE_GET_DYNAMIC_CONTENT_LIST = "getDynamicContentList";
	public static final String ACT_CODE_FIND_DUPLICATE_HN = "findDuplicateHn";
	public static final String ACT_CODE_FIND_DUPLICATE_BIO_REF = "findDuplicateBioRef";
	public static final String ACT_CODE_FIND_DUPLICATE_PATHO_NO = "findDuplicatePathoNo";
	public static final String ACT_CODE_GET_ACTIONABLE_VARIATNT_BY_GENE = "getActionableVariantByGene";
	public static final String ACT_CODE_GET_CANCER_GENE_GROUPS = "getCancerGeneGroups";
	public static final String ACT_CODE_GET_CLUSTER_ALGORITHMS  = "getClusterAlgorithms";
	public static final String ACT_CODE_GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER = "getRnaSeqListForClustergrammer";
	public static final String ACT_CODE_GET_ACTIVE_SAMPLE_RNA_SEQ_LIST = "getActiveSampleRnaSeqList";
	public static final String ACT_CODE_DOWNLOAD_FILE = "downloadFile";
	public static final String ACT_CODE_REGISTER_TO_DOWNLOAD = "registerToDownload";
	public static final String ACT_CODE_DOWNLOAD_SOURCE_CODE = "downloadSourceCode";
	public static final String ACT_CODE_GET_DATA_VERSIONS_BY_TYPE = "getDataVersionsByType";
	public static final String ACT_CODE_SEARCH_DATA_VERSION = "searchDataVersion";
	public static final String ACT_CODE_UPLOAD_ICD = "uploadIcd";
	public static final String ACT_CODE_FIND_DUPLICATE_DATA_VERSION = "findDuplicateDataVersion";
	public static final String ACT_CODE_DELETE_DATA_VERSION = "deleteDataVersion";
	public static final String ACT_CODE_UPLOAD_ICD_O = "uploadIcdO";
	public static final String ACT_CODE_DOWNLOAD_ICD_TEMPLATE = "downloadIcdTemplate";
	public static final String ACT_CODE_SEARCH_DYNAMIC_CONTENT = "searchDynamicContent";
	public static final String ACT_CODE_FIND_DUPLICATE_COMPONENT_NAME = "findDuplicateComponentName";
	public static final String ACT_CODE_SAVE_DYNAMIC_CONTENT = "saveDynamicContent";
	public static final String ACT_CODE_GET_VARIANT_CALLS_BY_SAMPLE_ID = "getVariantCallsBySampleId";
	public static final String ACT_CODE_ADD_NEW_ENTRY = "addNewEntry";
	public static final String ACT_CODE_SEARCH_USER = "searchUser";
	public static final String ACT_CODE_DELETE_USER = "deleteUser";
	public static final String ACT_CODE_GET_ACTIVE_ROLES = "getActiveRoles";
	public static final String ACT_CODE_FIND_DUPLICATE_USERNAME = "findDuplicateUsername";
	public static final String ACT_CODE_SAVE_USER = "saveUser";
	public static final String ACT_CODE_DELETE_DYNAMIC_CONTENT = "deleteDynamicContent";
	
	// Maxlength
	public static final int MAXLEN_BIO_REF = 20;
	public static final int MAXLEN_PATHO_NO = 20;
	public static final int MAXLEN_RESECTION_BIOPSY_SITE = 255;
	public static final int MAXLEN_EMAIL = 255;
	public static final int MAXLEN_FIRST_NAME = 150;
	public static final int MAXLEN_LAST_NAME = 150;
	public static final int MAXLEN_AFFILIATE = 255;
	public static final int MAXLEN_IS_AGREE = 1;
	public static final int MAXLEN_TOKEN = 255;
	
	public static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static final String[] IGNORE_ACTION_LIST = new String[] { ACT_CODE_LOGIN, ACT_CODE_GET_DYNAMIC_CONTENT_LIST, ACT_CODE_GET_APP_VERSION };
	
	public static final int BYTES_DOWNLOAD = 1024 * 100;
	public static final int BYTES_UPLOAD = 1024;
	
	public static final long MAX_FILE_UPLOAD_SIZE = 2L * 1024L * 1024L * 1024L; // 2GB
	
	public static final String SYSTEM_USER = "SYSTEM";
	
	public enum Parameter {
		T_STAGE("T_STAGE"),
		N_STAGE("N_STAGE"),
		M_STAGE("M_STAGE"),
		HISTOLOGIC_GRADE("HISTOLOGIC_GRADE"),
		AJCC("AJCC"),
		CLINICAL_STAGE("CLINICAL_STAGE"),
		PATHO_STAGE("PATHO_STAGE"),
		RECUR_STATUS("RECUR_STATUS"),
		STAGE_TYPE("STAGE_TYPE"),
		SUBSTAGE("SUBSTAGE");
		
		private final String value;

		public String getValue() {
			return value;
		}
		
		private Parameter(String value) {
			this.value = value;
		}
	}
	
	public enum DataAnalysis {
		CLUSTER_ALGORITHM("CLUSTER_ALGORITHM");
		
		private final String value;

		public String getValue() {
			return value;
		}
		
		private DataAnalysis(String value) {
			this.value = value;
		}
	}
}
