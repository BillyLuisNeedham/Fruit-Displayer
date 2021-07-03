package com.billyluisneedham.bbctest.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.models.FruitListResponse
import com.billyluisneedham.bbctest.source.remote.BaseDataSource.Companion.ERROR_MESSAGE
import com.billyluisneedham.bbctest.source.remote.service.Service
import com.billyluisneedham.bbctest.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RemoteFruitDataSourceTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockService = mockk<Service>()
    private lateinit var remoteFruitDataSource: RemoteFruitDataSource

    @Test
    fun successfulFetchFromServiceReturnsListOfFruitsWrappedInSuccessResource() {
        runBlocking {
            val successResponse = Response.success(MockFruit.mockFruitListResponse)

            coEvery { mockService.getFruits() } returns successResponse

            remoteFruitDataSource = RemoteFruitDataSource(mockService)


            val result = remoteFruitDataSource.getFruits()


            val expectedResult = Resource.success(MockFruit.mockFruitListResponse)

            assertThat(result, `is`(expectedResult))


        }
    }

    @Test
    fun failedFetchFromServiceReturnsStatusCodeAndMessage() {
        runBlocking {
            val failedResponse = initFailedResponse()

            coEvery { mockService.getFruits() } returns failedResponse

            remoteFruitDataSource = RemoteFruitDataSource(mockService)


            val result = remoteFruitDataSource.getFruits()


            val expectedResult = Resource.error<FruitListResponse>(ERROR_MESSAGE + "500 Response.error()")

            assertThat(result, `is`(expectedResult))

        }
    }

    private fun initFailedResponse(): Response<FruitListResponse> {
        return Response.error(
            500, "{\"message\":[\"internal error\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
    }

}