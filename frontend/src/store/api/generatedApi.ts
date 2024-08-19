import { emptySplitApi as api } from "./emptyApi";
export const addTagTypes = ["TrackDay", "options-controller"] as const;
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
      storeTrackDay: build.mutation<
        StoreTrackDayApiResponse,
        StoreTrackDayApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days`,
          method: "POST",
          body: queryArg.trackDayDto,
        }),
        invalidatesTags: ["TrackDay"],
      }),
      getTrackDaysByMonth: build.query<
        GetTrackDaysByMonthApiResponse,
        GetTrackDaysByMonthApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days/month/${queryArg.month}`,
        }),
        providesTags: ["TrackDay"],
      }),
      getTrackDayByDate: build.query<
        GetTrackDayByDateApiResponse,
        GetTrackDayByDateApiArg
      >({
        query: (queryArg) => ({
          url: `/api/track-days/date/${queryArg.trackDay}`,
        }),
        providesTags: ["TrackDay"],
      }),
      getDisturbanceOptions: build.query<
        GetDisturbanceOptionsApiResponse,
        GetDisturbanceOptionsApiArg
      >({
        query: () => ({ url: `/api/options/disturbance` }),
        providesTags: ["options-controller"],
      }),
      getCervixTextureOptions: build.query<
        GetCervixTextureOptionsApiResponse,
        GetCervixTextureOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/texture` }),
        providesTags: ["options-controller"],
      }),
      getCervixOpeningStateOptions: build.query<
        GetCervixOpeningStateOptionsApiResponse,
        GetCervixOpeningStateOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/opening-state` }),
        providesTags: ["options-controller"],
      }),
      getCervixHeightPositionOptions: build.query<
        GetCervixHeightPositionOptionsApiResponse,
        GetCervixHeightPositionOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervix/height-position` }),
        providesTags: ["options-controller"],
      }),
      getCervicalMucusOptions: build.query<
        GetCervicalMucusOptionsApiResponse,
        GetCervicalMucusOptionsApiArg
      >({
        query: () => ({ url: `/api/options/cervical-mucus` }),
        providesTags: ["options-controller"],
      }),
      getBleedingOptions: build.query<
        GetBleedingOptionsApiResponse,
        GetBleedingOptionsApiArg
      >({
        query: () => ({ url: `/api/options/bleeding` }),
        providesTags: ["options-controller"],
      }),
    }),
    overrideExisting: false,
  });
export { injectedRtkApi as api };
export type GetTrackDayApiResponse = /** status 200 OK */ TrackDay;
export type GetTrackDayApiArg = {
  id: number;
};
export type UpdateTrackDayApiResponse = /** status 200 OK */ object;
export type UpdateTrackDayApiArg = {
  id: number;
  trackDayDto: TrackDayDto;
};
export type DeleteTrackDayApiResponse = unknown;
export type DeleteTrackDayApiArg = {
  id: number;
};
export type GetTrackDaysApiResponse = /** status 200 OK */ TrackDay[];
export type GetTrackDaysApiArg = {
  pageable: Pageable;
};
export type StoreTrackDayApiResponse = /** status 200 OK */ object;
export type StoreTrackDayApiArg = {
  trackDayDto: TrackDayDto;
};
export type GetTrackDaysByMonthApiResponse = /** status 200 OK */ TrackDay[];
export type GetTrackDaysByMonthApiArg = {
  month: number;
};
export type GetTrackDayByDateApiResponse = /** status 200 OK */ TrackDay;
export type GetTrackDayByDateApiArg = {
  trackDay: string;
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
export type TrackDay = {
  id?: number;
  temperature: number;
  trackDay: string;
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
export type TrackDayDto = {
  temperature: number;
  trackDay: string;
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
export type Pageable = {
  page?: number;
  size?: number;
  sort?: string[];
};
export type OptionDto = {
  label: string;
  value: string;
  disabled?: boolean;
};
export enum BleedingEnum {
  Stark = "STARK",
  Mittel = "MITTEL",
  Schwach = "SCHWACH",
  Schmierblutung = "SCHMIERBLUTUNG",
}
export enum CervicalMucusEnum {
  Trocken = "TROCKEN",
  Normal = "NORMAL",
  Feucht = "FEUCHT",
  Cremig = "CREMIG",
  Spinnbar = "SPINNBAR",
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
  UngewohntSpaetesZubettgehen = "UNGEWOHNT_SPAETES_ZUBETTGEHEN",
  UngewohnterAlkoholgenuss = "UNGEWOHNTER_ALKOHOLGENUSS",
  Stress = "STRESS",
  SeelischeBelastung = "SEELISCHE_BELASTUNG",
  Zeitumstellung = "ZEITUMSTELLUNG",
  Zeitverschiebung = "ZEITVERSCHIEBUNG",
  KurzeOderGestoerteNachtruhe = "KURZE_ODER_GESTOERTE_NACHTRUHE",
  EssenSpaetabends = "ESSEN_SPAETABENDS",
  ReisenUndOderKlimawechsel = "REISEN_UND_ODER_KLIMAWECHSEL",
  ErkrankungUndUnpaesslichkeitenFieber = "ERKRANKUNG_UND_UNPAESSLICHKEITEN_FIEBER",
  AndereKrankheiten = "ANDERE_KRANKHEITEN",
  Medikament = "MEDIKAMENT",
  ThermometerwechselImZyklus = "THERMOMETERWECHSEL_IM_ZYKLUS",
  Aufregung = "AUFREGUNG",
  FeiernSpaetabends = "FEIERN_SPAETABENDS",
  Schichtarbeit = "SCHICHTARBEIT",
  Sonstiges = "SONSTIGES",
}
export const {
  useGetTrackDayQuery,
  useUpdateTrackDayMutation,
  useDeleteTrackDayMutation,
  useGetTrackDaysQuery,
  useStoreTrackDayMutation,
  useGetTrackDaysByMonthQuery,
  useGetTrackDayByDateQuery,
  useGetDisturbanceOptionsQuery,
  useGetCervixTextureOptionsQuery,
  useGetCervixOpeningStateOptionsQuery,
  useGetCervixHeightPositionOptionsQuery,
  useGetCervicalMucusOptionsQuery,
  useGetBleedingOptionsQuery,
} = injectedRtkApi;
