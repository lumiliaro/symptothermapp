import { Select, SelectProps } from "@mantine/core";
import dayjs from "dayjs";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useGetAllCycliQuery } from "../store/api/generatedApi";
import { setSelectedCyclusId } from "../store/Cyclus.Slice";
import { RootState } from "../store/store";
import SyInputSkeleton from "./SyInputSkeleton";

export default function SyCyclusStatisticSelect(props: SelectProps) {
    const { data: cycli } = useGetAllCycliQuery();
    const dispatch = useDispatch();
    const cyclusId = useSelector(
        (state: RootState) => state.cyclus.selectedCyclusId
    );

    useEffect(() => {
        if (!cyclusId && cycli && cycli[0]?.id) {
            dispatch(setSelectedCyclusId(cycli[0]?.id));
        }
    }, [cyclusId, cycli, dispatch]);

    if (!cycli) {
        return <SyInputSkeleton />;
    }

    const data =
        cycli?.map((cyclus) => ({
            label: dayjs(cyclus.date, "YYYY-MM-DD").format("DD.MM.YYYY"),
            value: cyclus.id?.toString() || "",
        })) || [];

    return (
        <Select
            data={data}
            label="Zyklus"
            placeholder="Bitte auswählen"
            size="md"
            color="blue"
            {...props}
            value={cyclusId ? cyclusId.toString() : null}
            onChange={(value) => {
                dispatch(setSelectedCyclusId(value ? parseInt(value) : null));
            }}
        />
    );
}
