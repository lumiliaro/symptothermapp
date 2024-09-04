import { Box, Center, Container, Tabs } from "@mantine/core";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import SyTrackDayDatePicker from "./components/SyTrackDayDatePicker";
import { useLazyGetTrackDayByDateQuery } from "./store/api/lazyApi";
import { RootState } from "./store/store";
import CyclusView from "./views/forms/Cyclus/view";
import TrackDayCreate from "./views/forms/TrackDay/create";
import TrackDayEdit from "./views/forms/TrackDay/edit";

export default function App() {
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
        <Container>
            <Tabs
                defaultValue="track"
                orientation="horizontal"
                keepMounted={false}
            >
                <Tabs.List>
                    <Tabs.Tab value="track">Erfassung</Tabs.Tab>
                    <Tabs.Tab value="cyclus">Zyklus</Tabs.Tab>
                </Tabs.List>
                <Tabs.Panel value="track">
                    <Box mt="sm" mb="lg">
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
                    <Box mt="sm" mb="lg">
                        <CyclusView />
                    </Box>
                </Tabs.Panel>
            </Tabs>
        </Container>
    );
}
