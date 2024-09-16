import { Box, Center, Tabs } from "@mantine/core";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import SyTrackDayDatePicker from "../components/SyTrackDayDatePicker";
import { useLazyGetTrackDayByDateQuery } from "../store/api/lazyApi";
import { RootState } from "../store/store";
import CyclusView from "./forms/Cyclus/view";
import TrackDayCreate from "./forms/TrackDay/create";
import TrackDayEdit from "./forms/TrackDay/edit";

export default function MainView() {
    const trackDayDate = useSelector((state: RootState) => state.trackDayDate);
    const [findTrackDayByDate, trackDay] = useLazyGetTrackDayByDateQuery();

    useEffect(() => {
        if (trackDayDate?.selectedDateString) {
            const fetchData = async (selectedDateString: string) => {
                await findTrackDayByDate({ day: selectedDateString });
            };

            void fetchData(trackDayDate?.selectedDateString);
        }
    }, [trackDayDate?.selectedDateString, findTrackDayByDate]);

    return (
        <Tabs defaultValue="track" orientation="horizontal" keepMounted={false}>
            <Tabs.List>
                <Tabs.Tab value="track">Erfassung</Tabs.Tab>
                <Tabs.Tab value="cyclus">Zyklus</Tabs.Tab>
            </Tabs.List>
            <Tabs.Panel value="track">
                <Box mt="lg" mb="lg">
                    <>
                        <Center>
                            <SyTrackDayDatePicker />
                        </Center>
                        {trackDay.data ? (
                            <TrackDayEdit data={trackDay?.data} />
                        ) : (
                            <TrackDayCreate />
                        )}
                    </>
                </Box>
            </Tabs.Panel>
            <Tabs.Panel value="cyclus">
                <Box mt="lg" mb="lg">
                    <CyclusView />
                </Box>
            </Tabs.Panel>
        </Tabs>
    );
}
