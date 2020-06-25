import { Injectable } from '@angular/core';
import { Dictionary } from '../bean/dictionary';

@Injectable()
export class Constants {

    DATE_FORMAT_DEFAULT = "YYYY-MM-DD";
    FORMAT_SHORT_DATE = "YYYY-MM-DD";
    FORMAT_TXN_DATE = "YYYYMMDDHHmmss";
    FORMAT_REF_NO = "YYYYMMDDHHmmssSSSS";

    CULTURE_SHORTNAME_THAI = "TH";
    CULTURE_SHORTNAME_ENGLISH = "EN";

    KNOWN_CULTURE_ENLISH_US = "en-US";
    KNOWN_CULTURE_THAI = "th-TH";

    STATUS_ACTIVE = "ACTIVE";
    STATUS_INACTIVE = "INACTIVE";
    STATUS_DRAFT = "DRAFT";

    SYSTEM_CODE = "ONC";
    CHANNEL_ID = "ONC_WEB";

    YES = "Y";
    NO = "N";

    IMAGE_PATH = "assets/images";

    MAX_FILE_UPLOAD_SIZE = 2147483648; // 2GB

    SAMPLE_TYPE: {
        BLOOD: string;
        TISSUE: string;
        CELL: string;
    };

    APP_CONFIG: {
        STEP_WIZARD: string;
        ALERT_MESSAGE: string;
    };

    IS_RECURRENCE = "RECUR_STATUS";

    COMPRESS_TYPE = {
        DEFLATE: 'DEFLATE',
        GZIP: 'GZIP'
    };

    CSV_LINE_SEPARATOR = '\r\n';
    CSV_COLUMN_SEPARATOR = ',';

    SERVICE_NAME: {
        GET_GENDER_LIST: string;
        GET_RACE_LIST: string;
        GET_TISSUE_PROCEDURE_LIST: string;
        GET_TISSUE_TYPE_LIST: string;
        GET_HISTOLOGIC_GRADE_LIST: string;
        GET_AJCC_LIST: string;
        GET_STAGE_TYPE_LIST: string;
        GET_CT_STAGE_LIST: string;
        GET_CN_STAGE_LIST: string;
        GET_PT_STAGE_LIST: string;
        GET_PN_STAGE_LIST: string;
        GET_YPT_STAGE_LIST: string;
        GET_YPN_STAGE_LIST: string;
        GET_M_STAGE_LIST: string;
        GET_CLINICAL_STAGE_LIST: string;
        GET_PATHOLOGICAL_STAGE_LIST: string;
        GET_VITAL_STATUS_LIST: string;
        GET_RECURRENCE_STATUS_LIST: string;
        GET_SAMPLE_TYPE_LIST: string;
        GET_BLOOD_TYPE_LIST: string;
        GET_CELL_TYPE_LIST: string;
        GET_CELL_LINE_TYPE_LIST: string;
        GET_FREEZE_METHOD_LIST: string;
        GET_PRESERVE_METHOD_LIST: string;
        SEARCH_ICD: string;
        SEARCH_ICD_O: string;
        SAVE_CLINICAL_DATA: string;
        SEARCH_CLINICAL_DATA: string;
        GET_CLINICAL_DATA: string;
        DELETE_CLINICAL_DATA: string;
        LOGIN: string;
        GET_APP_VERSION: string;
        SEARCH_PATIENT: string;
        GET_SEQ_TYPE_LIST: string;
        GET_SAMPLE_SOURCE_LIST: string;
        GET_SAMPLE_VCF_LIST_BY_PATIENT: string;
        DELETE_VARIANT_CALL: string;
        GET_VARIANT_CALL: string;
        SEARCH_PATIENT_BY_REF: string;
        SEARCH_BIOSPECIMEN: string;
        GET_CANCER_SUBSTAGE_LIST: string;
        DELETE_BIOSPECIMEN: string;
        GET_BIOSPECIMEN: string;
        SEARCH_PATIENT_PATHO_BY_PATHO_NO: string;
        SAVE_BIOSPECIMEN: string;
        GET_PATIENT: string;
        DELETE_PATIENT: string;
        SAVE_PATIENT: string;
        UPLOAD_SAMPLE_VCF: string;
        UPLOAD_SAMPLE_RNA_SEQ: string;
        GET_SAMPLE_RNA_SEQ_LIST: string;
        GET_RNA_SEQ_LIST: string;
        GET_INTERACTIONS: string;
        GET_VARIANT_COMPARISON: string;
        GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS: string;
        GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS: string;
        GET_DYNAMIC_CONTENT_LIST: string;
        FIND_DUPLICATE_HN: string;
        FIND_DUPLICATE_BIO_REF: string;
        FIND_DUPLICATE_PATHO_NO: string;
        GET_ACTIONABLE_VARIATNT_BY_GENE: string;
        GET_CANCER_GENE_GROUPS: string;
        GET_CLUSTER_ALGORITHMS: string;
        GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER: string;
        GET_ACTIVE_SAMPLE_RNA_SEQ_LIST: string;
        DOWNLOAD_FILE: string;
        REGISTER_TO_DOWNLOAD: string;
        DOWNLOAD_SOURCE_CODE: string;
        SEARCH_DATA_VERSION: string;
        FILTER_ICD: string;
        FILTER_ICD_O: string;
        DELETE_RNA_SEQ: string;
        UPLOAD_ICD: string;
        FIND_DUPLICATE_DATA_VERSION: string;
        DELETE_DATA_VERSION: string;
        UPLOAD_ICD_O: string;
        DOWNLOAD_ICD_TEMPLATE: string;
        SEARCH_DYNAMIC_CONTENT: string;
        FIND_DUPLICATE_COMPONENT_NAME: string;
        SAVE_DYNAMIC_CONTENT: string;
        CALC_TMB_SCORE: string;
        GET_VARIANT_CALLS_BY_SAMPLE_ID: string;
        SEARCH_USER: string;
        DELETE_USER: string;
        SAVE_USER: string;
        GET_ACTIVE_ROLES: string;
        FIND_DUPLICATE_USERNAME: string;
        DELETE_DYNAMIC_CONTENT: string;
    };

    VALIDATE: {
        SAVE_CLINICAL_DATA: {
            PATIENT: Array<string>;
            DIAGNOSIS_LIST: Array<string>;
            PATHOLOGICAL: Array<string>;
            CANCER_STAGE: Array<string>;
            CLINICAL_COURSE: Array<string>;
            SURVIVAL_FOLLOW_UP: Array<string>;
        },
        GET_INTERACTIONS: Array<string>
    };

    VALMSG: Dictionary = {
        'cy.type': 'Data source',
        'cy.genes': 'Genes'
    };

    URL: {
        TRANSCRIPT_BASED_URL: string;
        ONCOKB_BASED_URL: string;
        DRUG_BASED_URL: string;
    };

    CTX: {
        SECURITY: string;
        MASTER_DATA_MANAGEMENT: string;
    };

    APP_BASE_URL = '/api';
    DATA_ANLYS_BASE_URL = '/data-anlys-api';

    constructor() {
        this.APP_CONFIG = {
            STEP_WIZARD: "STEP_WIZARD",
            ALERT_MESSAGE: "ALERT_MESSAGE"
        };

        this.SERVICE_NAME = {
            GET_GENDER_LIST: "getGenderList",
            GET_RACE_LIST: "getRaceList",
            GET_TISSUE_PROCEDURE_LIST: "getTissueProcedureList",
            GET_TISSUE_TYPE_LIST: "getTissueTypeList",
            GET_HISTOLOGIC_GRADE_LIST: "getHistologicGradeList",
            GET_AJCC_LIST: "getAjccList",
            GET_STAGE_TYPE_LIST: "getStageTypeList",
            GET_CT_STAGE_LIST: "getCtStageList",
            GET_CN_STAGE_LIST: "getCnStageList",
            GET_PT_STAGE_LIST: "getPtStageList",
            GET_PN_STAGE_LIST: "getPnStageList",
            GET_YPT_STAGE_LIST: "getYptStageList",
            GET_YPN_STAGE_LIST: "getYpnStageList",
            GET_M_STAGE_LIST: "getMStageList",
            GET_CLINICAL_STAGE_LIST: "getClinicalStageList",
            GET_PATHOLOGICAL_STAGE_LIST: "getPathoStageList",
            GET_VITAL_STATUS_LIST: "getVitalStatusList",
            GET_RECURRENCE_STATUS_LIST: "getRecurrenceStatusList",
            GET_SAMPLE_TYPE_LIST: "getSampleTypeList",
            GET_BLOOD_TYPE_LIST: "getBloodTypeList",
            GET_CELL_TYPE_LIST: "getCellTypeList",
            GET_CELL_LINE_TYPE_LIST: "getCellLineTypeList",
            GET_FREEZE_METHOD_LIST: "getFreezeMethodList",
            GET_PRESERVE_METHOD_LIST: "getPreserveMethodList",
            SEARCH_ICD: "searchIcd",
            SEARCH_ICD_O: "searchIcdO",
            SAVE_CLINICAL_DATA: "saveClinicalData",
            SEARCH_CLINICAL_DATA: "searchClinicalData",
            GET_CLINICAL_DATA: "getClinicalData",
            DELETE_CLINICAL_DATA: "deleteClinicalData",
            LOGIN: "login",
            GET_APP_VERSION: "getAppVersion",
            SEARCH_PATIENT: "searchPatient",
            GET_SEQ_TYPE_LIST: "getSeqTypeList",
            GET_SAMPLE_SOURCE_LIST: "getSampleSourceList",
            GET_SAMPLE_VCF_LIST_BY_PATIENT: "getSampleVcfListByPatient",
            DELETE_VARIANT_CALL: "deleteVariantCall",
            GET_VARIANT_CALL: "getVariantCall",
            SEARCH_PATIENT_BY_REF: "searchPatientByRef",
            SEARCH_BIOSPECIMEN: "searchBiospecimen",
            GET_CANCER_SUBSTAGE_LIST: "getCancerSubstageList",
            DELETE_BIOSPECIMEN: 'deleteBiospecimen',
            GET_BIOSPECIMEN: 'getBiospecimen',
            SEARCH_PATIENT_PATHO_BY_PATHO_NO: 'searchPatientPathoByPathoNo',
            SAVE_BIOSPECIMEN: 'saveBiospecimen',
            GET_PATIENT: 'getPatient',
            DELETE_PATIENT: 'deletePatient',
            SAVE_PATIENT: 'savePatient',
            UPLOAD_SAMPLE_VCF: 'uploadSampleVcf',
            UPLOAD_SAMPLE_RNA_SEQ: 'uploadSampleRnaSeq',
            GET_SAMPLE_RNA_SEQ_LIST: 'getSampleRnaSeqList',
            GET_RNA_SEQ_LIST: 'getRnaSeqList',
            GET_INTERACTIONS: 'getInteractions',
            GET_VARIANT_COMPARISON: 'getVariantComparison',
            GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS: 'getSampleVcfListByBiospecimens',
            GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS: 'getSampleRnaSeqListByBiospecimens',
            GET_DYNAMIC_CONTENT_LIST: 'getDynamicContentList',
            FIND_DUPLICATE_HN: 'findDuplicateHn',
            FIND_DUPLICATE_BIO_REF: 'findDuplicateBioRef',
            FIND_DUPLICATE_PATHO_NO: 'findDuplicatePathoNo',
            GET_ACTIONABLE_VARIATNT_BY_GENE: 'getActionableVariantByGene',
            GET_CANCER_GENE_GROUPS: 'getCancerGeneGroups',
            GET_CLUSTER_ALGORITHMS: 'getClusterAlgorithms',
            GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER: 'getRnaSeqListForClustergrammer',
            GET_ACTIVE_SAMPLE_RNA_SEQ_LIST: 'getActiveSampleRnaSeqList',
            DOWNLOAD_FILE: 'downloadFile',
            REGISTER_TO_DOWNLOAD: 'registerToDownload',
            DOWNLOAD_SOURCE_CODE: 'downloadSourceCode',
            SEARCH_DATA_VERSION: 'searchDataVersion',
            FILTER_ICD: 'filterIcd',
            FILTER_ICD_O: 'filterIcdO',
            DELETE_RNA_SEQ: 'deleteRnaSeq',
            UPLOAD_ICD: 'uploadIcd',
            FIND_DUPLICATE_DATA_VERSION: 'findDuplicateDataVersion',
            DELETE_DATA_VERSION: 'deleteDataVersion',
            UPLOAD_ICD_O: 'uploadIcdO',
            DOWNLOAD_ICD_TEMPLATE: 'downloadIcdTemplate',
            SEARCH_DYNAMIC_CONTENT: 'searchDynamicContent',
            FIND_DUPLICATE_COMPONENT_NAME: 'findDuplicateComponentName',
            SAVE_DYNAMIC_CONTENT: 'saveDynamicContent',
            CALC_TMB_SCORE: 'calcTmbScore',
            GET_VARIANT_CALLS_BY_SAMPLE_ID: 'getVariantCallsBySampleId',
            SEARCH_USER: "searchUser",
            DELETE_USER: "deleteUser",
            SAVE_USER: "saveUser",
            GET_ACTIVE_ROLES: "getActiveRoles",
            FIND_DUPLICATE_USERNAME: "findDuplicateUsername",
            DELETE_DYNAMIC_CONTENT: "deleteDynamicContent"
        };

        this.VALIDATE = {
            SAVE_CLINICAL_DATA: {
                PATIENT: ["gender", "birthDate", "weight", "height", "race"],
                DIAGNOSIS_LIST: ["diagnosisDate", "primaryDiagnosis"],
                PATHOLOGICAL: [
                    "pathologyNo",
                    "tissueReportDate",
                    "resectionBiopsySite",
                    "tissueProcedure",
                    "tissueType",
                    "morphology",
                    "histologicGrade"
                ],
                CANCER_STAGE: ["stageType", "mStage", "pathologicalStage"],
                CLINICAL_COURSE: ["recurrenceStatus", "recurrenceDate"],
                SURVIVAL_FOLLOW_UP: ["vitalStatus", "lastFollowUpDate"]
            },
            GET_INTERACTIONS: ['type', 'genes']
        };

        this.URL = {
            TRANSCRIPT_BASED_URL: "http://asia.ensembl.org/Homo_sapiens/Transcript/Summary?t=",
            ONCOKB_BASED_URL: "https://oncokb.org/gene/",
            DRUG_BASED_URL: "https://pubchem.ncbi.nlm.nih.gov/compound/"
        };

        this.CTX = {
            SECURITY: "sec",
            MASTER_DATA_MANAGEMENT: "mdm"
        };

        // Biospecimen
        this.SAMPLE_TYPE = {
            BLOOD: '01',
            TISSUE: '02',
            CELL: '03'
        };
    }
}
