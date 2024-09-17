import { emptySplitApi as api } from "./emptyApi";
export const addTagTypes = ["TrackDay", "Options", "Server-Infor"] as const;
const injectedRtkApi = api
  .enhanceEndpoints({
    addTagTypes,
  })
  .injectEndpoints({
    endpoints: (build) => ({
      getTrackDay: build.query<GetTrackDayApiResponse, GetTrackDayApiArg>({
        query: (queryArg) => ({ url: `/api/track-days/${queryArg.id}` }),
        providesTags: ["TrackDay"],
      }),
      updateTrackDay: build.mutation<
        UpdateTrackDayApiResponse,
        UpdateTrackDayApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days/${queryArg.id}`,
          method: "PUT",
          body: queryArg.trackDayDto,
        }),
        invalidatesTags: ["TrackDay"],
      }),
      deleteTrackDay: build.mutation<
        DeleteTrackDayApiResponse,
        DeleteTrackDayApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days/${queryArg.id}`,
          method: "DELETE",
        }),
        invalidatesTags: ["TrackDay"],
      }),
      getTrackDays: build.query<GetTrackDaysApiResponse, GetTrackDaysApiArg>({
        query: (queryArg) => ({
          url: `/api/track-days`,
          params: { pageable: queryArg.pageable },
        }),
        providesTags: ["TrackDay"],
      }),
      createTrackDay: build.mutation<
        CreateTrackDayApiResponse,
        CreateTrackDayApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days`,
          method: "POST",
          body: queryArg.trackDayDto,
        }),
        invalidatesTags: ["TrackDay"],
      }),
      getTrackDaysByMonthAndYear: build.query<
        GetTrackDaysByMonthAndYearApiResponse,
        GetTrackDaysByMonthAndYearApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days/month/${queryArg.month}/${queryArg.year}`,
        }),
        providesTags: ["TrackDay"],
      }),
      getTrackDayMinMaxTemperature: build.query<
        GetTrackDayMinMaxTemperatureApiResponse,
        GetTrackDayMinMaxTemperatureApiArg
      >({
        query: () => ({ url: `/api/track-days/min-max-temperature` }),
        providesTags: ["TrackDay"],
      }),
      getTrackDayByDate: build.query<
        GetTrackDayByDateApiResponse,
        GetTrackDayByDateApiArg
      >({
        query: (queryArg) => ({ url: `/api/track-days/date/${queryArg.day}` }),
        providesTags: ["TrackDay"],
      }),
      getDisturbanceOptions: build.query<
        GetDisturbanceOptionsApiResponse,
        GetDisturbanceOptionsApiArg
      >({
        query: () => ({ url: `/api/options/disturbance` }),
        providesTags: ["Options"],
      }),
      getCervixTextureOptions: build.query<
        GetCervixTextureOptionsApiResponse,
        GetCervixTextureOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/texture` }),
        providesTags: ["Options"],
      }),
      getCervixOpeningStateOptions: build.query<
        GetCervixOpeningStateOptionsApiResponse,
        GetCervixOpeningStateOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/opening-state` }),
        providesTags: ["Options"],
      }),
      getCervixHeightPositionOptions: build.query<
        GetCervixHeightPositionOptionsApiResponse,
        GetCervixHeightPositionOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/height-position` }),
        providesTags: ["Options"],
      }),
      getCervicalMucusOptions: build.query<
        GetCervicalMucusOptionsApiResponse,
        GetCervicalMucusOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervical-mucus` }),
        providesTags: ["Options"],
      }),
      getBleedingOptions: build.query<
        GetBleedingOptionsApiResponse,
        GetBleedingOptionsApiArg
      >({
        query: () => ({ url: `/api/options/bleeding` }),
        providesTags: ["Options"],
      }),
      getServerInfo: build.query<GetServerInfoApiResponse, GetServerInfoApiArg>(
        {
          query: () => ({ url: `/api/info` }),
          providesTags: ["Server-Infor"],
        }
      ),
      getAllCycli: build.query<GetAllCycliApiResponse, GetAllCycliApiArg>({
        query: () => ({ url: `/api/cyclus` }),
        providesTags: ["TrackDay"],
      }),
      getOneCyclus: build.query<GetOneCyclusApiResponse, GetOneCyclusApiArg>({
        query: (queryArg) => ({ url: `/api/cyclus/${queryArg.id}` }),
        providesTags: ["TrackDay"],
      }),
      getCyclusStatisticById: build.query<
        GetCyclusStatisticByIdApiResponse,
        GetCyclusStatisticByIdApiArg
      >({
        query: (queryArg) => ({
          url: `/api/cyclus/statistic/${queryArg.cyclusId}`,
        }),
        providesTags: ["TrackDay"],
      }),
    }),
    overrideExisting: false,
  });
export { injectedRtkApi as api };
export type GetTrackDayApiResponse = /** status 200 OK */ TrackDay;
export type GetTrackDayApiArg = {
  id: number;
};
export type UpdateTrackDayApiResponse = /** status 200 OK */ void;
export type UpdateTrackDayApiArg = {
  id: number;
  trackDayDto: TrackDayDto;
};
export type DeleteTrackDayApiResponse = /** status 200 OK */ void;
export type DeleteTrackDayApiArg = {
  id: number;
};
export type GetTrackDaysApiResponse = /** status 200 OK */ PageTrackDay;
export type GetTrackDaysApiArg = {
  pageable: Pageable;
};
export type CreateTrackDayApiResponse = /** status 200 OK */ void;
export type CreateTrackDayApiArg = {
  trackDayDto: TrackDayDto;
};
export type GetTrackDaysByMonthAndYearApiResponse =
  /** status 200 OK */ TrackDay[];
export type GetTrackDaysByMonthAndYearApiArg = {
  month: number;
  year: number;
};
export type GetTrackDayMinMaxTemperatureApiResponse =
  /** status 200 OK */ TrackDayMinMaxTemperatureDto;
export type GetTrackDayMinMaxTemperatureApiArg = void;
export type GetTrackDayByDateApiResponse = /** status 200 OK */ TrackDay;
export type GetTrackDayByDateApiArg = {
  day: string;
};
export type GetDisturbanceOptionsApiResponse = /** status 200 OK */ OptionDto[];
export type GetDisturbanceOptionsApiArg = void;
export type GetCervixTextureOptionsApiResponse =
  /** status 200 OK */ OptionDto[];
export type GetCervixTextureOptionsApiArg = void;
export type GetCervixOpeningStateOptionsApiResponse =
  /** status 200 OK */ OptionDto[];
export type GetCervixOpeningStateOptionsApiArg = void;
export type GetCervixHeightPositionOptionsApiResponse =
  /** status 200 OK */ OptionDto[];
export type GetCervixHeightPositionOptionsApiArg = void;
export type GetCervicalMucusOptionsApiResponse =
  /** status 200 OK */ OptionDto[];
export type GetCervicalMucusOptionsApiArg = void;
export type GetBleedingOptionsApiResponse = /** status 200 OK */ OptionDto[];
export type GetBleedingOptionsApiArg = void;
export type GetServerInfoApiResponse = /** status 200 OK */ ServerInfoDto;
export type GetServerInfoApiArg = void;
export type GetAllCycliApiResponse = /** status 200 OK */ Cyclus[];
export type GetAllCycliApiArg = void;
export type GetOneCyclusApiResponse = /** status 200 OK */ Cyclus;
export type GetOneCyclusApiArg = {
  id: number;
};
export type GetCyclusStatisticByIdApiResponse =
  /** status 200 OK */ CyclusStatisticDto[];
export type GetCyclusStatisticByIdApiArg = {
  cyclusId: number;
};
export type TrackDay = {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  day: string;
  temperature?: number;
  bleeding?: BleedingEnum;
  cervicalMucus?: CervicalMucusEnum;
  cervixOpeningState?: CervixOpeningStateEnum;
  cervixHeightPosition?: CervixHeightPositionEnum;
  cervixTexture?: CervixTextureEnum;
  hadSex?: boolean;
  withContraceptives?: boolean;
  disturbances?: DisturbanceEnum[];
  otherDisturbanceNotes?: string;
  notes?: string;
};
export type ErrorDto = {
  /** Fehlermeldung */
  message?: string;
  /** Fehlercode */
  code?: string;
  /** Zeitstempel des Fehlers */
  timestamp?: string;
  /** HTTP-Statuscode */
  status?: number;
};
export type TrackDayDto = {
  temperature?: number;
  day: string;
  bleeding?: BleedingEnum;
  cervicalMucus?: CervicalMucusEnum;
  cervixOpeningState?: CervixOpeningStateEnum;
  cervixHeightPosition?: CervixHeightPositionEnum;
  cervixTexture?: CervixTextureEnum;
  hadSex: boolean;
  withContraceptives: boolean;
  disturbances?: DisturbanceEnum[];
  otherDisturbanceNotes?: string;
  notes?: string;
};
export type SortObject = {
  direction?: string;
  nullHandling?: string;
  ascending?: boolean;
  property?: string;
  ignoreCase?: boolean;
};
export type PageableObject = {
  offset?: number;
  sort?: SortObject[];
  paged?: boolean;
  pageSize?: number;
  pageNumber?: number;
  unpaged?: boolean;
};
export type PageTrackDay = {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  content?: TrackDay[];
  number?: number;
  sort?: SortObject[];
  first?: boolean;
  last?: boolean;
  numberOfElements?: number;
  pageable?: PageableObject;
  empty?: boolean;
};
export type Pageable = {
  page?: number;
  size?: number;
  sort?: string[];
};
export type TrackDayMinMaxTemperatureDto = {
  minTemperature?: number;
  maxTemperature?: number;
};
export type OptionDto = {
  label: string;
  value: string;
  disabled?: boolean;
};
export type ServerInfoDto = {
  version?: string;
};
export type Cyclus = {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  date: string;
};
export type CyclusStatisticDto = {
  cyclusDay: string;
  date: string;
  temperature?: number;
  cervicalMucus?: string;
  bleeding?: string;
  fertile?: boolean;
  createdAt?: string;
  updatedAt?: string;
};
export enum BleedingEnum {
  Strong = "STRONG",
  Medium = "MEDIUM",
  Weak = "WEAK",
  SpottingBleeding = "SPOTTING_BLEEDING",
}
export enum CervicalMucusEnum {
  Dry = "DRY",
  Normal = "NORMAL",
  Moist = "MOIST",
  Creamy = "CREAMY",
  Spinnable = "SPINNABLE",
}
export enum CervixOpeningStateEnum {
  Open = "OPEN",
  PartiallyOpen = "PARTIALLY_OPEN",
  Closed = "CLOSED",
}
export enum CervixHeightPositionEnum {
  High = "HIGH",
  Low = "LOW",
}
export enum CervixTextureEnum {
  Hard = "HARD",
  Soft = "SOFT",
}
export enum DisturbanceEnum {
  GoingToBedUnusuallyLate = "GOING_TO_BED_UNUSUALLY_LATE",
  UnusualAlcoholConsumption = "UNUSUAL_ALCOHOL_CONSUMPTION",
  Stress = "STRESS",
  MentalStress = "MENTAL_STRESS",
  TimeChange = "TIME_CHANGE",
  TimeShift = "TIME_SHIFT",
  ShortOrShortNightRest = "SHORT_OR_SHORT_NIGHT_REST",
  EatingLateEvenings = "EATING_LATE_EVENINGS",
  TravelAndChangeOfClimate = "TRAVEL_AND_CHANGE_OF_CLIMATE",
  IllnessAndUnpleasantFever = "ILLNESS_AND_UNPLEASANT_FEVER",
  OtherIllnesses = "OTHER_ILLNESSES",
  Medicine = "MEDICINE",
  ThermometerChangeInTheCycle = "THERMOMETER_CHANGE_IN_THE_CYCLE",
  Excitement = "EXCITEMENT",
  CelebrationEvenings = "CELEBRATION_EVENINGS",
  ShiftWork = "SHIFT_WORK",
  Other = "OTHER",
}
export const {
  useGetTrackDayQuery,
  useUpdateTrackDayMutation,
  useDeleteTrackDayMutation,
  useGetTrackDaysQuery,
  useCreateTrackDayMutation,
  useGetTrackDaysByMonthAndYearQuery,
  useGetTrackDayMinMaxTemperatureQuery,
  useGetTrackDayByDateQuery,
  useGetDisturbanceOptionsQuery,
  useGetCervixTextureOptionsQuery,
  useGetCervixOpeningStateOptionsQuery,
  useGetCervixHeightPositionOptionsQuery,
  useGetCervicalMucusOptionsQuery,
  useGetBleedingOptionsQuery,
  useGetServerInfoQuery,
  useGetAllCycliQuery,
  useGetOneCyclusQuery,
  useGetCyclusStatisticByIdQuery,
} = injectedRtkApi;
